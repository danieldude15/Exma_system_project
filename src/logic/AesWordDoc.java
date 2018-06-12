package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class AesWordDoc extends XWPFDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9193230217538325507L;

	public AesWordDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AesWordDoc(InputStream is) throws IOException {
		super(is);
		// TODO Auto-generated constructor stub
	}

	public AesWordDoc(OPCPackage pkg) throws IOException {
		super(pkg);
		// TODO Auto-generated constructor stub
	}

	

}
