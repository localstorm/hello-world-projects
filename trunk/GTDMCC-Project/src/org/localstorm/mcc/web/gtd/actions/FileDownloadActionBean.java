package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.files.FileManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/DownloadFile")
public class FileDownloadActionBean extends GtdBaseActionBean
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
