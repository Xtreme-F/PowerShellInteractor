package lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import tool.StreamReader;

public class PowerShellProcess {
	
	public static String command = "Add-Type -AssemblyName System.Speech; (New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak('Hello');";
	public static String command1 = "Add-Type -AssemblyName System.Speech;";
	public static String command2 = "$somevoice = (New-Object System.Speech.Synthesis.SpeechSynthesizer);";
	public static String command3 = "$somevoice.GetInstalledVoices().VoiceInfo | Select-Object -Property Name;";
	public static String command4 = "$somevoice.SelectVoice('Microsoft David Desktop');";
	public static String command5 = "$somevoice.Speak('Hello, Welcome to the Speech Synthesis World!');";
	
	StreamReader inputreader;
	StreamReader errreader;
	
	BufferedWriter writer;
	BufferedReader reader;
	BufferedReader errorreader;
	
	Process process;
	ProcessBuilder builder;
	
	private ArrayList<String> outputlines;
	
	public PowerShellProcess() throws IOException{
		//"-NoExit" , "-OutputFormat"
		outputlines = new ArrayList<String>();
		builder = new ProcessBuilder("powershell.exe", "-InputFormat", "Text", "-Command", "-");
		
		process = builder.start();
		
		//OutputStream stdin = process.getOutputStream();
		//InputStream stderr = process.getErrorStream();
		
		writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		
		inputreader = new StreamReader(process.getInputStream(), System.out, outputlines);
		inputreader.start();
		errreader = new StreamReader(process.getErrorStream(), System.err, outputlines);
		errreader.start();
	}
	
	public void writeCommand(String cmd) throws Exception{
		for (int i = 0; i < 1; i++) {
		    //Thread.sleep(5000);
		    writer.write(cmd + "\n");
			writer.flush();
			//writer.flush();
		}
		
	}
	
	public void endProcess() {
		process.destroy();
	}
	
	public ArrayList<String> retrieveOutput(){
    	return outputlines;
    }
    
    public void flushOutput() {
    	outputlines = new ArrayList<String>();
    }
	
	public static void main(String[] args) {
		try {
			PowerShellProcess pro = new PowerShellProcess();
			System.out.println("after constructor");
			//Thread.sleep(5000);
			pro.writeCommand(PowerShellProcess.command1);
			System.out.println("after write command");
			pro.writeCommand(PowerShellProcess.command2);
			System.out.println("after write command");
			pro.writeCommand(PowerShellProcess.command3);
			System.out.println("after write command");
			pro.writeCommand(PowerShellProcess.command4);
			System.out.println("after write command");
			pro.writeCommand(PowerShellProcess.command5);
			System.out.println("after write command");
			pro.writer.close();
		}
		catch(Exception e) {
			System.out.println("catch");
			e.printStackTrace();
		}
	}
}
