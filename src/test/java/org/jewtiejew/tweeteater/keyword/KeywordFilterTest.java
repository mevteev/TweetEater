package org.jewtiejew.tweeteater.keyword;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by mike on 08.06.18.
 */
public class KeywordFilterTest {

    @InjectMocks
    KeywordFilter filter;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void testFilter() {
        assertTrue(filter.test("dsfddsfg"));
        assertFalse(filter.test("11"));
        assertFalse(filter.test("http"));
        assertFalse(filter.test("https"));


    }
}
