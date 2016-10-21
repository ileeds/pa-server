package edu.Brandeis.cs131.Common.IanLeeds;

import edu.Brandeis.cs131.Common.Abstract.Industry;

public class BasicClient extends MyClient {

	public BasicClient(String label, Industry industry) {
		super(label, industry);
	}
    
    public String toString(){
		return String.format("%s BASIC %s", this.getIndustry(), this.getName());
    }
}
