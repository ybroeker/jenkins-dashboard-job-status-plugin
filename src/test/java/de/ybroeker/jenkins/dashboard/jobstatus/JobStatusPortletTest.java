package de.ybroeker.jenkins.dashboard.jobstatus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;


public class JobStatusPortletTest {

    @Test
    public void shouldCalculatePercentage() {
        JobStatusPortlet jobStatusPortlet = new JobStatusPortlet("-");

        final float percentage = jobStatusPortlet.getPercentage(5, 10);
        assertThat(percentage).isCloseTo(50.0f, offset(0.00001f));
    }

    @Test
    public void shouldCalculatePercentageForZeroValue() {
        JobStatusPortlet jobStatusPortlet = new JobStatusPortlet("-");

        final float percentage = jobStatusPortlet.getPercentage(0, 100);
        assertThat(percentage).isCloseTo(0.0f, offset(0.00001f));
    }

    @Test
    public void shouldNotFailOnZeroBuilds() {
        JobStatusPortlet jobStatusPortlet = new JobStatusPortlet("-");

        final float percentage = jobStatusPortlet.getPercentage(0, 0);
        assertThat(percentage).isCloseTo(0.0f, offset(0.00001f));
    }

}
