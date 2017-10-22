package com.lichader.test.alfred.integration.metro;

import com.lichader.alfred.type.MetroAlfred;
import com.lichader.test.alfred.AbstractSpringBootTestBase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

@Category(AbstractSpringBootTestBase.class)
public class MetroServiceAbstractSpringBootTestBase extends AbstractSpringBootTestBase {

    @Autowired
    private MetroAlfred metroAlfred;

    @Test
    public void runIt(){
        metroAlfred.checkDisruption();
    }
}
