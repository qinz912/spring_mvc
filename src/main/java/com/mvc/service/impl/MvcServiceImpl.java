package com.mvc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.bo.LiliProject;
import com.mvc.bo.ServerResponse;
import com.mvc.service.IMvcService;
import com.mvc.util.PropertiesUtil;

@Service
public class MvcServiceImpl implements IMvcService {
	private static Integer KINDS_COl;
	private static Integer TOUCH_TIME_COL;
	private static Integer MODEL_TIME_COL;
	private static Integer AMOUNT_COL;
	private static Integer NUM_COL;
	private static Integer  HANDLE_COL;
	private static Integer WAY_COL;
	private static String PRO_NUM;
	private static String STEP;
	
	static{
		KINDS_COl = Integer.parseInt(PropertiesUtil.getProperty("kinds.col", "3"));
		TOUCH_TIME_COL = Integer.parseInt(PropertiesUtil.getProperty("touchTime.col", "4"));
		MODEL_TIME_COL = Integer.parseInt(PropertiesUtil.getProperty("modelTime.col", "5"));
		AMOUNT_COL = Integer.parseInt(PropertiesUtil.getProperty("amount.col", "6"));
		NUM_COL = Integer.parseInt(PropertiesUtil.getProperty("num.col", "7"));
		HANDLE_COL = Integer.parseInt(PropertiesUtil.getProperty("handler.col", "8"));
		WAY_COL = Integer.parseInt(PropertiesUtil.getProperty("way.col", "9"));
		PRO_NUM = PropertiesUtil.getProperty("proNum");
		STEP = PropertiesUtil.getProperty("step","2");
	}
	
	public String doUpload(MultipartFile file){
		try {

			if(!file.isEmpty()){
				HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream()); 
				HSSFSheet sheet = null;
		        int i = workbook.getSheetIndex("sheet1"); // sheet����
		        sheet = workbook.getSheetAt(i);
		        HSSFCellStyle cellStyle = workbook.createCellStyle();
		        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //�±߿�    
		        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//��߿�    
		        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//�ϱ߿�    
		        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//�ұ߿�    
		        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����  
		        
		       List<LiliProject> proList = this.readXml();
		       String tempNum = "0";
		        
		        for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
		        	HSSFRow row = sheet.getRow(j);
		            if (row == null) {
		            	continue;
		            }
		            String val = row.getCell(KINDS_COl).toString();
	            	for(LiliProject pro:proList){
	            		if(pro.getKinds().equals(val)){
	            			HSSFCell handlerCell = row.getCell(HANDLE_COL);
	            			HSSFCell wayCell = row.getCell(WAY_COL);
	            			HSSFCell numCell = row.getCell(NUM_COL);
	            			handlerCell.setCellValue(pro.getHandler());
	            			handlerCell.setCellStyle(cellStyle);
	            			
	            			wayCell.setCellValue(pro.getWay());
	            			wayCell.setCellStyle(cellStyle);
	            			
	            			tempNum = changeNum(tempNum);
	            			numCell.setCellValue(pro.getNum()+PRO_NUM+tempNum+"-"+addStep(tempNum));
	            			tempNum = addStep(tempNum);
	            			numCell.setCellStyle(cellStyle);
	            		}
	            	}
		        }
		        FileOutputStream os = new FileOutputStream(PropertiesUtil.getProperty("lili.url", "D:")+"\\temp\\version"+System.currentTimeMillis()+".xls");  
		        os.flush();
		        workbook.write(os);  
		        //�ر���  
		        os.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error";
		}
		return "success";
	}
	
	public List<LiliProject> readXml(){
		List<LiliProject> projectList = new ArrayList();
		SAXReader reader = new SAXReader();
		try {//this.getClass().getClassLoader().getResource("") contact.xml resources
			Document document = reader.read(new File("e:apache-tomcat-7.0.85/webapps/spring_mvc/WEB-INF/classes/contact.xml"));
			 Element root = document.getRootElement();
			 List<Element> list = root.elements();
			 for (Element e:list){
				 String kinds = e.element("kinds").getStringValue();
				 String touchTime = e.element("touchTime").getStringValue();
				 String modelTime = e.element("modelTime").getStringValue();
				 String amount = e.element("amount").getStringValue();
				 String handler = e.element("handler").getStringValue();
				 String way = e.element("way").getStringValue();
				 String num = e.element("num").getStringValue();
				 LiliProject liliPro = new LiliProject();
				 liliPro.setKinds(kinds);
				 liliPro.setTouchTime(touchTime);
				 liliPro.setModelTime(modelTime);
				 liliPro.setAmount(amount);
				 liliPro.setHandler(handler);
				 liliPro.setWay(way);
				 liliPro.setNum(num);
				 projectList.add(liliPro);
			 }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return projectList;
	}
	
	private String changeNum(String startNum){
		int i = Integer.parseInt(startNum);
		i++;
		DecimalFormat df=new DecimalFormat("0000");
		String result=df.format(i);
		return result;
	}
	
	private String addStep(String num){
		int i = Integer.parseInt(num);
		int h = Integer.parseInt(STEP);
		i = i+h;
		return i+"";
		
	}
	
	public ServerResponse<String> readExcelFromDoc(MultipartFile file){
        try {
        	//��xml�ж�ȡ����
        	List<LiliProject> proList = this.readXml();
            //����docxģ�岢��ȡdocument����
        	String url = "C:/Users/Administrator/Desktop/�ϵۻ����ֳ���ⷽ��.docx";
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            //�������
            List<XWPFTable> tables = document.getTables();
            for (int i = 0; i < tables.size(); i++) {
            	XWPFTable table = tables.get(i);
                 if(table.getRows().size()>1){
                	 String tempNum = "0";
                	 List<XWPFTableRow> rows = table.getRows();
                	 for (int k=0;k<rows.size();k++) { 
                		 if(k == 0 ){
                			 continue;
                		 }
                		 XWPFTableRow row = rows.get(k);
                		 List<XWPFTableCell> cells = row.getTableCells();
                		 if(cells.size()<4){
                			 continue;
                		 }
                		 XWPFTableCell kindsCell = cells.get(KINDS_COl);
                		 String kinds = kindsCell.getText();
                		 if(kinds == null || "".equals(kinds)){
                			 return ServerResponse.createByErrorMessage("������û����д����ȥ���һ�±��");
                		 }
                		 for(LiliProject lili : proList){
                			 String liKinds = lili.getKinds();
                			 if(kinds.equals(liKinds)){
                				 tempNum = changeNum(tempNum);
                				 tempNum = setValue(lili,cells,tempNum);
                			 }
                		 }
                	 }
                 }
            }
            File outFile = new File(
    		PropertiesUtil.getProperty("lili.url", "C:\\Users\\Administrator\\Desktop\\")+
    		"version"+System.currentTimeMillis()+".docx");
            FileOutputStream stream = new FileOutputStream(outFile);
            document.write(stream);
            stream.close();
            return ServerResponse.createBySuccessByMessage("�����ĵ��ɹ�����ȥ�����ɣ�");
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("�ļ���ȡ����"+e.getMessage());
        }
    }
	
	
	private String setValue(LiliProject lili, List<XWPFTableCell> cells, String num){
		String addStep = addStep(num); 
		for (int j = 0 ;j < cells.size(); j++) {
			 if(j == TOUCH_TIME_COL){
				 XWPFTableCell cell = cells.get(j);
				 cell.setText(lili.getTouchTime());
			 }else if(j == MODEL_TIME_COL){
				 XWPFTableCell cell = cells.get(j);
				 cell.setText(lili.getModelTime());
			 }else if(j == AMOUNT_COL){
				 XWPFTableCell cell = cells.get(j);
				 cell.setText(lili.getAmount());
			 }else if(j == NUM_COL){
				 XWPFTableCell cell = cells.get(j);
				 cell.setText(lili.getNum()+PRO_NUM+num+"-"+addStep);
			 }else if(j == HANDLE_COL){
				 XWPFTableCell cell = cells.get(j);
				 cell.setText(lili.getHandler());
			 }else if(j == WAY_COL){
				 XWPFTableCell cell = cells.get(j);
				 cell.setText(lili.getWay());
			 }
			 
		 }
		
		return addStep;
	}

}
