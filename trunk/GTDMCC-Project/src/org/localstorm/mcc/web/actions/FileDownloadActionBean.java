package org.localstorm.mcc.web.actions;

import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.files.FileAttachment;
import org.localstorm.mcc.ejb.files.FileManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/DownloadFile")
public class FileDownloadActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int fileId;

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {
        HttpServletResponse resp = this.getContext().getResponse();
        FileManager fm           = this.getFileManager();
        
        FileAttachment fa = fm.findAttachmentById(fileId);
        if (fa==null)
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        
        resp.setContentType(fa.getMimeType());
        resp.addHeader("Content-Disposition", "attachment; filename=\""+fa.getName()+"\"");
        fm.download(fa, resp.getOutputStream());
        
        return null;
    }

    
    public static interface IncommingParameters {
        public static final String FILE_ID = "fileId";
    }
    
}