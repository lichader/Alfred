package com.lichader.test.alfred.metro;

import com.lichader.alfred.type.MetroAlfred;
import com.lichader.test.alfred.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MetroServiceIntegrationTest extends IntegrationTest{

    @Autowired
    private MetroAlfred metroAlfred;

    @Test
    public void runIt(){
        metroAlfred.checkDisruption();
    }
}
