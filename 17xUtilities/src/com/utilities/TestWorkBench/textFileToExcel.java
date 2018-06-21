package com.utilities.TestWorkBench;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.framework.ui.UIUtil;
import com.utilities.dataProcessingUtils.processThumbnailInfo;

import matrix.util.StringList;

public class textFileToExcel {

    public static void main(String[] args) throws Exception 
    {
    	List<Map<?, ?>> mlInput = new ArrayList<Map<?, ?>>();
    	List<Map<?, ?>> mlInput1 = new ArrayList<Map<?, ?>>();
    	

        try 
        {
        	

            File f = new File("C:\\Users\\502244529\\Documents\\SX Validation - RefactorTest.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            System.out.println("Reading file using Buffered Reader");

            int i =0;
           
            StringList slTemp = new StringList();
            StringList slTemp1 = new StringList();
            while ((readLine = b.readLine()) != null) 
            {
            	Map mTemp = new HashMap();   

            	slTemp = FrameworkUtil.split(readLine, ",");
            	for(int j=0; j<slTemp.size();j++)
            	{
            		
            		slTemp1 = FrameworkUtil.split(((String)slTemp.get(j)).trim(), "=");
            		mTemp.put((String)slTemp1.get(0), (String)slTemp1.get(1));
            		
            	}
            	mlInput.add(mTemp);
            }

            File f1 = new File("C:\\Users\\502244529\\Documents\\SX Validation - RefactorTest - ThumbnailDetails.txt");

            BufferedReader b1 = new BufferedReader(new FileReader(f1));

            String readLine1 = "";

            System.out.println("Reading file using Buffered Reader");

            int i1 =0;
           
            StringList slTemp1_1 = new StringList();
            StringList slTemp2_1 = new StringList();
            while ((readLine1 = b1.readLine()) != null) 
            {
            	Map mTemp1 = new HashMap();   
            	slTemp1_1 = FrameworkUtil.split(readLine1, ",");
            	
            	for(int j=0; j<slTemp1_1.size();j++)
            	{
            		slTemp2_1 = FrameworkUtil.split(((String)slTemp1_1.get(j)).trim(), "=");
            		mTemp1.put((String)slTemp2_1.get(0), (String)slTemp2_1.get(1));
            		
            	}
            	mlInput1.add(mTemp1);
            }
            processThumbnailInfo.rearrangeThumbnailData(mlInput, mlInput1);
        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
    }
    
    
    public static void writeToExcel(List<Map<?, ?>> mlInput) throws IOException {
    	
    	System.out.println("Inside writeToExcel >>");
    	StringList slTemp1 = new StringList();
    	
    	 String[] columns = {"Type", "Name", "Revision","Part Number", "Description", "Owner", "Originated","Modified","Usage","3Sh Type","3Sh Name","3Sh Revision","3Sh Part Number","3Sh Owner","3Sh Originated","3Sh Modified","3Sh Usage","3Sh File Info 1","3Sh File Info 2","Dwg Type","Dwg Name","Dwg Revision","Dwg Part Number","Dwg Owner","Dwg Originated","Dwg Modified","Dwg Usage","Dwg File Info 1","Dwg File Info 2"};
    	 
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances for various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("6-21-18");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(int ml=0; ml<mlInput.size();ml++) {
        	
            Map mTemp = (Map)mlInput.get(ml);
            Row row = sheet.createRow(rowNum++);

			row.createCell(0)
			    .setCellValue((String)mTemp.get(DomainConstants.SELECT_TYPE));
			
			row.createCell(1)
			        .setCellValue((String)mTemp.get(DomainConstants.SELECT_NAME));
			
			row.createCell(2)
			.setCellValue((String)mTemp.get(DomainConstants.SELECT_REVISION));
			
			row.createCell(3)
			.setCellValue((String)mTemp.get("attribute[PLMEntity.V_Name].value"));
			
			row.createCell(4)
			.setCellValue((String)mTemp.get("attribute[PLMEntity.V_description].value"));
			
			row.createCell(5)
			.setCellValue((String)mTemp.get(DomainConstants.SELECT_OWNER));
			
			Cell originatedCell = row.createCell(6);
			originatedCell.setCellValue((String)mTemp.get(DomainConstants.SELECT_ORIGINATED));
			originatedCell.setCellStyle(dateCellStyle);
			
			Cell modifiedCell = row.createCell(7);
			modifiedCell.setCellValue((String)mTemp.get(DomainConstants.SELECT_MODIFIED));
			modifiedCell.setCellStyle(dateCellStyle);
			
			row.createCell(8)
			.setCellValue((String)mTemp.get("attribute[PLMEntity.V_usage].value"));
			
			System.out.println("Test Insance"+(String)mTemp.get("from[VPMRepInstance].to.type"));
			//PP has More than one Rep -- Start
			if(UIUtil.isNotNullAndNotEmpty((String)mTemp.get("from[VPMRepInstance].to.type")) && ((String)mTemp.get("from[VPMRepInstance].to.type")).contains(""))
			{
				slTemp1 = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "");
				//3DShape Comes First in selectable -- Start
				if(((String)slTemp1.get(0)).equals("3DShape"))
				{
					//Fill 3DShape Information when 1st Part of Type is 3DShape -- Start
					row.createCell(9)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(0));
					
					row.createCell(10)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(0));
					
					row.createCell(11)
				    .setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
					
					row.createCell(12)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
					
					row.createCell(13)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(0));
					
					Cell shapeOriginatedCell = row.createCell(14);
					shapeOriginatedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(0));
					shapeOriginatedCell.setCellStyle(dateCellStyle);
					
					Cell shapeModifiedCell = row.createCell(15);
					shapeModifiedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(0));
					shapeModifiedCell.setCellStyle(dateCellStyle);
					
					row.createCell(16)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(0));
					
					//Write 3DShape File Information -- Start
					List iIndiceslist = getIndices((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), '');
					if(iIndiceslist.size() == 3)//Both Representaions's Files got loaded to v6 Format
					{
						String s3ShTemp = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of 3DShape
						
						StringList sl3ShTemp2 = FrameworkUtil.split(s3ShTemp, "");
						
						row.createCell(17)
						.setCellValue((String)sl3ShTemp2.get(0));
						
						String s3ShTemp2 = ((String)sl3ShTemp2.get(1)).replaceAll("", "");
						
						row.createCell(18)
						.setCellValue(s3ShTemp2);
						
						//Write DWG File Info -- Start
						
						String sDwgTemp = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get 2Files of Drawing
						
						StringList slDwgTemp = FrameworkUtil.split(sDwgTemp, "");
												
						row.createCell(27)
						.setCellValue((String)slDwgTemp.get(0));
						
						String sDwgTemp1 = ((String)slDwgTemp.get(1)).replaceAll("", "");
						
						row.createCell(28)
						.setCellValue(sDwgTemp1);
						
						//Write DWG File Info -- End
								
					}
					else if(iIndiceslist.size() == 2)//1 Representation File got converted to v6 Format
					{
						String s3ShTemp3 = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of 3DShape
//						System.out.println("s3ShTemp3 >>>"+s3ShTemp3);
						StringList sl3ShTemp3 = FrameworkUtil.split(s3ShTemp3, "");
						if(((String)sl3ShTemp3.get(0)).contains("CATIA")) //3DShape Failed, Drawing Passed
						{
							row.createCell(17)
							.setCellValue((String)sl3ShTemp3.get(0));
							
							//Write DWG Files -- Start
							String sDwgTemp2 = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(0)); //get 2Files of Drawing
							System.out.println("sDwgTemp2 >>"+sDwgTemp2);
							StringList slDwgTemp2 = FrameworkUtil.split(sDwgTemp2, "");
							
							row.createCell(27)
							.setCellValue((String)slDwgTemp2.get(0));
							
							String sDwgTemp3 = ((String)slDwgTemp2.get(1)).replaceAll("", "");
							
							row.createCell(28)
							.setCellValue(sDwgTemp3);
							
							//Write DWG Files -- End
							
						}
						else //3DShape Pass, Drawing Failed
						{
							row.createCell(17)
							.setCellValue((String)sl3ShTemp3.get(0));
							
							String s3ShTemp4 = ((String)sl3ShTemp3.get(1)).replaceAll("", "");
							
							row.createCell(18)
							.setCellValue(s3ShTemp4);
							
							//Write DWG File Info -- Start
							String sDwgTemp4 = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get File of Drawing
							row.createCell(27)
							.setCellValue(sDwgTemp4);
							
							//Write DWG File Info -- End
						}
					}
					else if(iIndiceslist.size() == 1)//Both Representations Failed
					{
						StringList slTemp4 = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), "");
						row.createCell(17)
						.setCellValue((String)slTemp4.get(0));
						
						//Write DWG File Info -- Start
						row.createCell(27)
						.setCellValue((String)slTemp4.get(1));
						//Write DWG File Info -- End
					}
					//Write 3DShape File Information -- End
					//Fill 3DShape Information when 1st Part of Type is 3DShape -- End
					
					//Fill Drawing Information when 1st Part of Type is 3DShape -- Start
					
					row.createCell(19)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
					
					row.createCell(20)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
					
					row.createCell(21)
				    .setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
					
					row.createCell(22)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
					
					row.createCell(23)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
					
					Cell dwgOriginatedCell = row.createCell(24);
					dwgOriginatedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
					dwgOriginatedCell.setCellStyle(dateCellStyle);
					
					Cell dwgModifiedCell = row.createCell(25);
					dwgModifiedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
					dwgModifiedCell.setCellStyle(dateCellStyle);
					
					row.createCell(26)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));

				}
				//3DShape Comes First in selectable -- End
				//Drawing Comes First in Selectable  -- Start
				else if(((String)slTemp1.get(0)).equals("Drawing"))
				{

					//Fill 3DShape Information when 1st Part of Type is 3DShape -- Start
					row.createCell(9)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(1));
					
					row.createCell(10)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(1));
					
					row.createCell(11)
				    .setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(1));
					
					row.createCell(12)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(1));
					
					row.createCell(13)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(1));
					
					Cell shapeOriginatedCell = row.createCell(14);
					shapeOriginatedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(1));
					shapeOriginatedCell.setCellStyle(dateCellStyle);
					
					Cell shapeModifiedCell = row.createCell(15);
					shapeModifiedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(1));
					shapeModifiedCell.setCellStyle(dateCellStyle);
					
					row.createCell(16)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(1));
					
					//Write 3DShape File Information -- Start
					List iIndiceslist = getIndices((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), '');
					if(iIndiceslist.size() == 3)//Both Representaions's Files got loaded to v6 Format
					{
						String s3ShTemp = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of 3DShape
						
						StringList sl3ShTemp2 = FrameworkUtil.split(s3ShTemp, "");
						
						row.createCell(27)
						.setCellValue((String)sl3ShTemp2.get(0));
						
						String s3ShTemp2 = ((String)sl3ShTemp2.get(1)).replaceAll("", "");
						
						row.createCell(28)
						.setCellValue(s3ShTemp2);
						
						//Write DWG File Info -- Start
						
						String sDwgTemp = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get 2Files of Drawing
						
						StringList slDwgTemp = FrameworkUtil.split(sDwgTemp, "");
												
						row.createCell(17)
						.setCellValue((String)slDwgTemp.get(0));
						
						String sDwgTemp1 = ((String)slDwgTemp.get(1)).replaceAll("", "");
						
						row.createCell(18)
						.setCellValue(sDwgTemp1);
						
						//Write DWG File Info -- End
								
					}
					else if(iIndiceslist.size() == 2)//1 Representation File got converted to v6 Format
					{
						String s3ShTemp3 = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring(0, (int)iIndiceslist.get(1)); //get 2Files of 3DShape
//						System.out.println("s3ShTemp3 >>>"+s3ShTemp3);
						StringList sl3ShTemp3 = FrameworkUtil.split(s3ShTemp3, "");
						if(((String)sl3ShTemp3.get(0)).contains("CATIA")) //3DShape Failed, Drawing Passed
						{
							row.createCell(27)
							.setCellValue((String)sl3ShTemp3.get(0));
							
							//Write DWG Files -- Start
							String sDwgTemp2 = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(0)); //get 2Files of Drawing
							System.out.println("sDwgTemp2 >>"+sDwgTemp2);
							StringList slDwgTemp2 = FrameworkUtil.split(sDwgTemp2, "");
							
							row.createCell(17)
							.setCellValue((String)slDwgTemp2.get(0));
							
							String sDwgTemp3 = ((String)slDwgTemp2.get(1)).replaceAll("", "");
							
							row.createCell(18)
							.setCellValue(sDwgTemp3);
							
							//Write DWG Files -- End
							
						}
						else //3DShape Pass, Drawing Failed
						{
							row.createCell(27)
							.setCellValue((String)sl3ShTemp3.get(0));
							
							String s3ShTemp4 = ((String)sl3ShTemp3.get(1)).replaceAll("", "");
							
							row.createCell(28)
							.setCellValue(s3ShTemp4);
							
							//Write DWG File Info -- Start
							String sDwgTemp4 = ((String)mTemp.get("from[VPMRepInstance].to.format.file.name")).substring((int)iIndiceslist.get(1)); //get File of Drawing
							row.createCell(17)
							.setCellValue(sDwgTemp4);
							
							//Write DWG File Info -- End
						}
					}
					else if(iIndiceslist.size() == 1)//Both Representations Failed
					{
						StringList slTemp4 = FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.format.file.name"), "");
						row.createCell(27)
						.setCellValue((String)slTemp4.get(0));
						
						//Write DWG File Info -- Start
						row.createCell(17)
						.setCellValue((String)slTemp4.get(1));
						//Write DWG File Info -- End
					}
					//Write 3DShape File Information -- End
					//Fill 3DShape Information when 1st Part of Type is 3DShape -- End
					
					//Fill Drawing Information when 1st Part of Type is 3DShape -- Start
					
					row.createCell(19)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.type"), "").get(0));
					
					row.createCell(20)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.name"), "").get(0));
					
					row.createCell(21)
				    .setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.revision"), "").get(0));
					
					row.createCell(22)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"), "").get(0));
					
					row.createCell(23)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.owner"), "").get(0));
					
					Cell dwgOriginatedCell = row.createCell(24);
					dwgOriginatedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.originated"), "").get(0));
					dwgOriginatedCell.setCellStyle(dateCellStyle);
					
					Cell dwgModifiedCell = row.createCell(25);
					dwgModifiedCell.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.modified"), "").get(0));
					dwgModifiedCell.setCellStyle(dateCellStyle);
					
					row.createCell(26)
					.setCellValue((String)FrameworkUtil.split((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"), "").get(0));

				}
				//Drawing Comes First in Selectable  -- End
			}
			//PP has More than one Rep -- End
			//PP has Only 3DShape -- Start
			if("3DShape".equalsIgnoreCase((String)mTemp.get("from[VPMRepInstance].to.type")))
			{
				row.createCell(9)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.type"));
				
				row.createCell(10)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.name"));
				
				row.createCell(11)
			    .setCellValue((String)mTemp.get("from[VPMRepInstance].to.revision"));
				
				row.createCell(12)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"));
				
				row.createCell(13)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.owner"));
				
				Cell shapeOriginatedCell = row.createCell(14);
				shapeOriginatedCell.setCellValue((String)mTemp.get("from[VPMRepInstance].to.originated"));
				shapeOriginatedCell.setCellStyle(dateCellStyle);
				
				Cell shapeModifiedCell = row.createCell(15);
				shapeModifiedCell.setCellValue((String)mTemp.get("from[VPMRepInstance].to.modified"));
				shapeModifiedCell.setCellStyle(dateCellStyle);
				
				row.createCell(16)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"));
				
				//3DShape File Conversion Failed
				String s3ShTemp = (String)mTemp.get("from[VPMRepInstance].to.format.file.name");
				if(s3ShTemp.contains("CATIA"))
				{
					row.createCell(17)
					.setCellValue(s3ShTemp);
				}
				else if(s3ShTemp.contains("")) //3DShape File Conversion Passed
				{
					StringList sl3ShTemp = FrameworkUtil.split(s3ShTemp, "");
					
					row.createCell(17)
					.setCellValue((String)sl3ShTemp.get(0));
					
					row.createCell(18)
					.setCellValue((String)sl3ShTemp.get(1));
				}
				
			}
			//PP has Only 3DShape -- End
			
			//PP has Only Drawing -- Start
			
			if("Drawing".equalsIgnoreCase((String)mTemp.get("from[VPMRepInstance].to.type")))
			{
				row.createCell(19)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.type"));
				
				row.createCell(20)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.name"));
				
				row.createCell(21)
			    .setCellValue((String)mTemp.get("from[VPMRepInstance].to.revision"));
				
				row.createCell(22)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"));
				
				row.createCell(23)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.owner"));
				
				Cell dwgOriginatedCell = row.createCell(24);
				dwgOriginatedCell.setCellValue((String)mTemp.get("from[VPMRepInstance].to.originated"));
				dwgOriginatedCell.setCellStyle(dateCellStyle);
				
				Cell dwgModifiedCell = row.createCell(25);
				dwgModifiedCell.setCellValue((String)mTemp.get("from[VPMRepInstance].to.modified"));
				dwgModifiedCell.setCellStyle(dateCellStyle);
				
				row.createCell(26)
				.setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"));
				
				//3DShape File Conversion Failed
				String s3ShTemp = (String)mTemp.get("from[VPMRepInstance].to.format.file.name");
				if(s3ShTemp.contains("CATIA"))
				{
					row.createCell(27)
					.setCellValue(s3ShTemp);
				}
				else if(s3ShTemp.contains("")) //3DShape File Conversion Passed
				{
					StringList sl3ShTemp = FrameworkUtil.split(s3ShTemp, "");
					
					row.createCell(27)
					.setCellValue((String)sl3ShTemp.get(0));
					
					row.createCell(28)
					.setCellValue((String)sl3ShTemp.get(1));
				}
				
			}
			
			if(UIUtil.isNotNullAndNotEmpty("from[VPMInstance].to.type"))
			{
				row.createCell(29)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.type"));
				
				row.createCell(30)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.name"));
				
				row.createCell(31)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.revision"));

				row.createCell(32)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.attribute[PLMEntity.V_Name].value"));
				
				row.createCell(33)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.attribute[PLMEntity.V_description].value"));
				
				row.createCell(34)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.owner"));
				
				row.createCell(35)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.originated"));
				
				row.createCell(36)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.modified"));
				
				row.createCell(37)
				.setCellValue((String)mTemp.get("from[VPMInstance].to.attribute[PLMEntity.V_usage].value"));
				
				
			}
			//PP has Only Drawing -- End
			
			/*row.createCell(8)
			.setCellValue((String)mTemp.get("from[VPMRepInstance].to.type"));			
			
			row.createCell(9)
			.setCellValue((String)mTemp.get("from[VPMRepInstance].to.name"));
			
			
			row.createCell(10)
			.setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_Name].value"));
			
			row.createCell(11)
			    .setCellValue((String)mTemp.get("from[VPMRepInstance].to.revision"));
			
			row.createCell(12)
			.setCellValue((String)mTemp.get("from[VPMRepInstance].to.owner"));
			
			
			Cell repOriginatedCell = row.createCell(13);
			repOriginatedCell.setCellValue((String)mTemp.get("from[VPMRepInstance].to.originated"));
			repOriginatedCell.setCellStyle(dateCellStyle);
			
			Cell repModifiedCell = row.createCell(14);
			repModifiedCell.setCellValue((String)mTemp.get("from[VPMRepInstance].to.modified"));
			repModifiedCell.setCellStyle(dateCellStyle);
			
			row.createCell(15)
			.setCellValue((String)mTemp.get("from[VPMRepInstance].to.attribute[PLMEntity.V_usage].value"));
			
			row.createCell(16)
			.setCellValue((String)mTemp.get("from[VPMRepInstance].to.format.file.name"));
			    */
        }

		// Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("SX-Validation-QA1-6-21-18.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("CLOSE");

        // Closing the workbook
//        workbook.close();
    }


    public static List getIndices(String input, char toBeFound)

    {
    	List<Integer> iRetList = new ArrayList<Integer>();
       int[] retArray = new int[400] ;
        for (int i = 0 ; i<input.length() ; i++)
        {
	        if (input.charAt(i) == toBeFound)
	        {
		        //System.out.println(i);
	        	iRetList.add(i);
		        //System.out.println("retArray>>>"+retArray[i]);
	        }
        }
        
        return iRetList;
    }
    
    
    public static void dwgInfoToExcel( String[] columns, List<Map<?, ?>> mlshrdDwgInput) throws IOException {
    	
    	System.out.println("Inside dwgInfoToExcel :::: "+mlshrdDwgInput);
    	System.out.println("Inside writeToExcel >>");
    	
    	StringList slTemp1 = new StringList();
    	
    	String isShared = "";
    	
//    	 String[] columns = {"Type", "Name", "Revision","Part Number", "Description", "Owner", "Originated","Modified","Usage","Is Shared", "File Info 1","File Info 2","PP Type","PP Name","PP Revision","PP Part Number","PP Description", "PP Owner","PP Originated","PP Modified","PP Usage"};
    	 
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances for various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("6-21-18");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        
        //Create Cell Wrap Text style
        CellStyle wrapStyle = workbook.createCellStyle();
        wrapStyle.setWrapText(true);

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(int ml=0; ml<mlshrdDwgInput.size();ml++) 
        {
        	
        	Map mTemp = (Map)mlshrdDwgInput.get(ml);
            Row row = sheet.createRow(rowNum++);

			row.createCell(0)
			    .setCellValue((String)mTemp.get(DomainConstants.SELECT_TYPE));
			
			row.createCell(1)
		    .setCellValue((String)mTemp.get(DomainConstants.SELECT_NAME));
			
			row.createCell(2)
		    .setCellValue((String)mTemp.get(DomainConstants.SELECT_REVISION));
			
			row.createCell(3)
		    .setCellValue((String)mTemp.get("attribute[PLMEntity.V_Name].value"));
			
			row.createCell(4)
			.setCellValue((String)mTemp.get("attribute[PLMEntity.V_description].value"));
			
			
			row.createCell(5)
			.setCellValue((String)mTemp.get(DomainConstants.SELECT_OWNER));
			
			Cell dwgOriginatedCell = row.createCell(6);
			dwgOriginatedCell.setCellValue((String)mTemp.get(DomainConstants.SELECT_ORIGINATED));
			dwgOriginatedCell.setCellStyle(dateCellStyle);
			
			Cell dwgModifiedCell = row.createCell(7);
			dwgModifiedCell.setCellValue((String)mTemp.get(DomainConstants.SELECT_MODIFIED));
			dwgModifiedCell.setCellStyle(dateCellStyle);
			
			
			row.createCell(8)
			.setCellValue((String)mTemp.get("attribute[PLMEntity.V_usage].value"));
			
			//Populate Is Shared Value -- Start
			if(("TRUE".equalsIgnoreCase((String)mTemp.get("attribute[PLMCoreRepReference.V_isOnceInstantiable].value"))))
			{
				isShared = "No";
			}
			
			else if(("FALSE".equalsIgnoreCase((String)mTemp.get("attribute[PLMCoreRepReference.V_isOnceInstantiable].value"))))
			{
				isShared = "Yes";
			}
			//Populate Is Shared Value -- End
			
			row.createCell(9)
			.setCellValue(isShared);
			
			if(mTemp.get("format.file.name") instanceof StringList)
			{
				StringList slFiles = (StringList)mTemp.get("format.file.name");
				
				String sFile1 = "";
				String sFile2 = "";

				if(slFiles.size() == 2)
				{
					sFile1 = (String)slFiles.get(0);
					sFile2 = (String)slFiles.get(1);
					
					row.createCell(10)
					.setCellValue(sFile1);
					
					row.createCell(11)
					.setCellValue(sFile2);
					
				}
				else if(slFiles.size() == 1)
				{
					sFile1 = (String)slFiles.get(0);
					
					row.createCell(10)
					.setCellValue(sFile1);
				}
//				System.out.println("slFiles :::"+slFiles);
//				System.out.println("DWG NAME - Multiple Files :::"+(String)mTemp.get(DomainConstants.SELECT_NAME));
			}
			
			else if(mTemp.get("format.file.name") instanceof String)
			{
				String sFile = (String)mTemp.get("format.file.name");
				
//				System.out.println("sFile :::"+sFile);
//				System.out.println("DWG NAME - Single File :::"+(String)mTemp.get(DomainConstants.SELECT_NAME));
			}

			//If Drawing is connected to multiple Parts/Products  -- Start
			if(UIUtil.isNotNullAndNotEmpty((String)mTemp.get("to[VPMRepInstance].from.type")))
			{	
				if ((((String)mTemp.get("to[VPMRepInstance].from.type")).contains("")))
				{
			
					Cell connectedPPTypeCell = row.createCell(12);
					connectedPPTypeCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.type")).replaceAll("", "\r\n"));
					connectedPPTypeCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPNameCell = row.createCell(13);
					connectedPPNameCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.name")).replaceAll("", "\r\n"));
					connectedPPNameCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPRevisionCell = row.createCell(14);
					connectedPPRevisionCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.revision")).replaceAll("", "\r\n"));
					connectedPPRevisionCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPPrtNumberCell = row.createCell(15);
					connectedPPPrtNumberCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.attribute[PLMEntity.V_Name].value")).replaceAll("", "\r\n"));
					connectedPPPrtNumberCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPDescpCell = row.createCell(16);
					connectedPPDescpCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.attribute[PLMEntity.V_description].value")).replaceAll("", "\r\n"));
					connectedPPDescpCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPOwnerCell = row.createCell(17);
					connectedPPOwnerCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.owner")).replaceAll("", "\r\n"));
					connectedPPOwnerCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPOriginatedCell = row.createCell(18);
					connectedPPOriginatedCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.originated")).replaceAll("", "\r\n"));
					connectedPPOriginatedCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPModifiedCell = row.createCell(19);
					connectedPPModifiedCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.modified")).replaceAll("", "\r\n"));
					connectedPPModifiedCell.setCellStyle(wrapStyle);
					
					
					Cell connectedPPUsageCell = row.createCell(20);
					connectedPPUsageCell.setCellValue(((String)mTemp.get("to[VPMRepInstance].from.attribute[PLMEntity.V_usage].value")).replaceAll("", "\r\n"));
					connectedPPUsageCell.setCellStyle(wrapStyle);
				}
				
			
			//If Drawing is connected to multiple Parts/Products  -- End
			
			//If Drawing is connected to Single Part/Product  -- Start
			else 
			{
				(row.createCell(12))
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.type"));
				
				(row.createCell(13))
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.name"));
				
				row.createCell(14)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.revision"));
				
				row.createCell(15)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.attribute[PLMEntity.V_Name].value"));
				
				row.createCell(16)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.attribute[PLMEntity.V_description].value"));
				
				row.createCell(17)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.owner"));
				
				row.createCell(18)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.originated"));
				
				row.createCell(19)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.modified"));
				
				row.createCell(20)
				.setCellValue((String)mTemp.get("to[VPMRepInstance].from.attribute[PLMEntity.V_usage].value"));
			}
			//If Drawing is connected to Single Part/Product  -- End
		}
    }
        
        for(int i = 0; i < columns.length; i++) 
        {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("SX-Validation-DWG-QA1-6-21-18.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("CLOSE");

        // Closing the workbook
//        workbook.close();
       }
    	
    }


