package edu.Brandeis.cs131.Common.IanLeeds;

import edu.Brandeis.cs131.Common.Abstract.Industry;

public class SharedClient extends MyClient {

	public SharedClient(String label, Industry industry) {
		super(label, industry);
	}
    
    public String toString(){
		return String.format("%s SHARED %s", this.getIndustry(), this.getName());
    }
}
