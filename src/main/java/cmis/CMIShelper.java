package cmis;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.activiti.engine.ActivitiException;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

public class CMIShelper {
	private static final String ALFRESCO_CMIS_URL = "http://localhost:9090/alfresco/api/-default-/cmis/versions/1.0/atom";
	private static final String ALFRESCO_ADMIN_PASSWORD = "ines";
	
	public Folder documentFolder;
	
	private Session session;
	/*private InputStream sheetInputStream;
	private XSSFWorkbook excelWorkbook;
	private Sheet sheet;
	private XSSFFormulaEvaluator evaluator;*/
	
	 public void createCmisSession() {
		  	session = (Session) CmisUtil.createCmisSession("admin", ALFRESCO_ADMIN_PASSWORD, ALFRESCO_CMIS_URL);
		  }
	 /*private Folder getFolder(String folderName) {
		  	Folder parentFolder = CmisUtil.getFolder(session, "loanapplication");
		    Folder folder = containsFolderWithName(folderName, parentFolder);
		    if(folder == null) {
		    	folder = CmisUtil.createFolder(session, parentFolder, folderName);
		    }
		    return folder;
		  }*/

	 public Document saveDocumentToFolder(ByteArrayOutputStream documentStream, String folderId, String name, 
		  		String fileSuffix, String mimeType) { 	
		  	try {
			    byte[] content = documentStream.toByteArray();
			    Folder folder = (Folder)session.getObject(folderId);
			    return CmisUtil.createDocument(session, folder, name +
			    		"-" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + fileSuffix, 
			    		mimeType, content);
		    } catch(Exception e) {
		    	throw new ActivitiException("Error storing document in CMIS repository", e);
		    } finally {
		    	try {
		    		documentStream.close();
		    	} catch(Exception e) {}
		    }
		  }
	
}
