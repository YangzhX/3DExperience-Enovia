package com.utilites.reportUtils;

import java.io.FileOutputStream;
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

import com.matrixone.apps.framework.ui.UIUtil;

public class ListToExcel {
	
	public static void NestedListsWithMapsToExcel(String WorkBookName, String SheetName, String WookBookDestination, String[] columns, List<Map<?,?>> mlInput) throws Exception
	{
		// Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        
        /* CreationHelper helps us create instances for various things like DataFormat, 
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();
        
        // Create a Sheet
        Sheet sheet = workbook.createSheet(SheetName);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREEN.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells(columns)
        for(int i = 0; i < columns.length; i++) 
        {
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
        
        int rowNum = 1;
        int cellNum = 0;
        if(mlInput != null && mlInput.size()>0) //Main MapList -- input maplist -- which contains one list for each PP
        {
        	for(int imlInput=0; imlInput < mlInput.size(); imlInput++)//Main MapList -- input maplist -- which contains one list for each PP
        	{
        		Object o = mlInput.get(imlInput);//Get List of each PP
        		if(o instanceof List)
        		{
        			List lTemp = (List) o;
					Row row = sheet.createRow(rowNum++);
					cellNum = 0;
        			if(lTemp != null && lTemp.size()>0)
        			{
        				for(int ilTemp=0; ilTemp < lTemp.size(); ilTemp++)//Iterate through list of each PP and get the info from 3 Different maps
        				{
        					Map mTemp = (HashMap)lTemp.get(ilTemp);
        					
        					if(mTemp != null && mTemp.size()>0)
        					{
        						for(int imTemp = 0 ; imTemp < mTemp.size(); imTemp++)
        						{
        							Cell dataCell = row.createCell(cellNum);
        							dataCell.setCellValue((String)mTemp.get(imTemp));
        							
        							if(!("".equals((String)mTemp.get(imTemp))) && !("null".equals((String)mTemp.get(imTemp))) && (String)mTemp.get(imTemp) != null  && ((String)mTemp.get(imTemp)).contains("\r\n"))
        							{
        								dataCell.setCellStyle(wrapStyle);
        							}
        							cellNum++;
        						}
        					}
        					else
        					{
        						cellNum = cellNum + 9;
        					}
        				}//Iterate through list of each PP and get the info from 3 Different maps
        			}
        		}//Get List of each PP
        	}//Main MapList -- input maplist -- which contains one list for each PP
        }//Main MapList -- input maplist -- which contains one list for each PP
        
        
     // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) 
        {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(WookBookDestination + WorkBookName + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("CLOSE");

        // Closing the workbook
//        workbook.close();
	}
	
	
	public static void ListWithMapToExcel(String WorkBookName, String SheetName, String WookBookDestination, String[] columns, List<Map<?,?>> mlInput) throws Exception
	{
		System.out.println("Inside List with Map to Excel :::");
		
		System.out.println("Inside List with Map to Excel ::: WorkBookName ::: "+WorkBookName);
		// Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        
        /* CreationHelper helps us create instances for various things like DataFormat, 
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();
        
        // Create a Sheet
        Sheet sheet = workbook.createSheet(SheetName);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREEN.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells(columns)
        for(int i = 0; i < columns.length; i++) 
        {
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
        
        int rowNum = 1;
        int cellNum = 0;
        
        if(mlInput != null && mlInput.size()>0) //Main MapList -- input maplist
        {
        	for(int imlInput=0; imlInput < mlInput.size(); imlInput++)//Iterate through Maplist and get each map
        	{
        		Map mTemp = (HashMap)mlInput.get(imlInput);
        		
        		if(mTemp != null && mTemp.size()>0)
        		{
        			Row row = sheet.createRow(rowNum++);
        			cellNum = 0;
        			
	        		for(int imTemp = 0; imTemp < mTemp.size(); imTemp++)
	        		{
	    				Cell dataCell = row.createCell(cellNum);
						dataCell.setCellValue((String)mTemp.get(imTemp));
						
						if(((String)mTemp.get(imTemp)).contains("\r\n"))
						{
							dataCell.setCellStyle(wrapStyle);
						}
						cellNum++;
	        		}
        		}
        	}
        }
        
        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) 
        {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(WookBookDestination + WorkBookName + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("CLOSE");

        // Closing the workbook
        // workbook.close();
	}
}
