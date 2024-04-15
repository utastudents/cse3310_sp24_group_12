package uta.cse3310;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

    @Test
    public void testEnumValues(){
        //Check if expected values are present
        Assert.assertTrue(Color.RED != null);
        Assert.assertTrue(Color.GREEN != null);
        Assert.assertTrue(Color.BLUE != null);
        Assert.assertTrue(Color.ORANGE != null);
        Assert.assertTrue(Color.YELLOW != null);
        Assert.assertTrue(Color.PURPLE != null);
        Assert.assertTrue(Color.WHITE != null);
    }

    @Test
    public void testEnumOrder(){
        //Check order of colors 
        Color[] colors = Color.values();
        Assert.assertEquals(Color.RED, colors[0]);
        Assert.assertEquals(Color.GREEN, colors[1]);
        Assert.assertEquals(Color.BLUE, colors[2]);
        Assert.assertEquals(Color.ORANGE, colors[3]);
        Assert.assertEquals(Color.YELLOW, colors[4]);
        Assert.assertEquals(Color.PURPLE, colors[5]);
        Assert.assertEquals(Color.WHITE, colors[6]);
    }
}
