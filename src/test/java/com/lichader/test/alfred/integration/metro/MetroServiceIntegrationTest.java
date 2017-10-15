package com.lichader.test.alfred.integration.metro;

import com.lichader.alfred.type.MetroAlfred;
import com.lichader.test.alfred.integration.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

@Category(IntegrationTest.class)
public class MetroServiceIntegrationTest extends IntegrationTest{

    @Autowired
    private MetroAlfred metroAlfred;

    @Test
    public void runIt(){
        metroAlfred.checkDisruption();
    }
}
