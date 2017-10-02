package Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import TestCases.TestNG_Reports_TC;

public class Logger extends TestNG_Reports_TC {
	
	public Logger(String result){
		
	}
	
	public void createFile(String resultado) throws Exception{
		
		File file = new File(executionResultsPath + "logFile.txt");
		  BufferedWriter bw;
	        if(file.exists()) {
	            bw = new BufferedWriter(new FileWriter(file));
	            bw.write(resultado);
	            System.out.println(" Archivo Sobreescrito!!");			
	        } else {
	            bw = new BufferedWriter(new FileWriter(file));
	            bw.write(resultado);
	            System.out.println(" Archivo Creado!!");			
	        }
	        bw.close();
		    }
		    
		
		
	
	}

