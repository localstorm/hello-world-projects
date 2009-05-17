/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.tools.crash;

/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExceptionHandlerPrototype ehp = new StdoutHandlerPrototype();

        // Find the root thread group
        ThreadGroup root = getAccessibleThreadGroupRoot();
        while (root.getParent() != null) {
            root = root.getParent();
        }

        // Visit each thread group
        visit(root, 0, new InjectFirstExceptionHandler(ehp));


        throw new RuntimeException("Fuck!");
    }

    // This method recursively visits all thread groups under `group'.
    public static void visit(ThreadGroup group, int level, ThreadCommand tc) {
        // Get threads in `group'
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads*2];
        numThreads = group.enumerate(threads, false);

        // Enumerate each thread in `group'
        for (int i=0; i<numThreads; i++) {
            // Get thread
            Thread thread = threads[i];
            tc.handle(thread);
        }

        // Get thread subgroups of `group'
        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups*2];
        numGroups = group.enumerate(groups, false);

        // Recursively visit each subgroup
        for (int i=0; i<numGroups; i++) {
            visit(groups[i], level+1, tc);
        }
    }

  

    private static ThreadGroup getAccessibleThreadGroupRoot()
    {
        ThreadGroup group  = Thread.currentThread().getThreadGroup();
        if (!SecurityUtil.checkAccess(group)) {
            return null;
        }

        ThreadGroup parent = group;

        do{
            if (!SecurityUtil.checkAccess(parent)) {
                return group;
            }

            parent = parent.getParent();

        } while (parent!=null);
        return group;
    }

}
