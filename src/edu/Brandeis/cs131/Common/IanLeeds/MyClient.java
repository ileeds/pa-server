package edu.Brandeis.cs131.Common.IanLeeds;

import java.util.Random;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Industry;
import edu.Brandeis.cs131.Common.Test.TestUtilities;

public abstract class MyClient extends Client {

    public MyClient(String label, Industry industry) {
        super(label, industry, new Random().nextInt(10), 3);
    }
}
