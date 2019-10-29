package de.ybroeker.jenkins.dashboard.jobstatus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JobStatusPortletTest {

    JobStatusPortlet jobStatusPortlet = new JobStatusPortlet("-");

    @Test
    public void shouldCalculatePercentage() {
        final String percentage = jobStatusPortlet.getPercentage(5, 10);
        assertThat(percentage).isEqualTo("50%");
    }

    @Test
    public void shouldPrintTwoFractionDigits() {
        final String percentage = jobStatusPortlet.getPercentage(3, 9);
        assertThat(percentage).isEqualTo("33.33%");
    }

    @Test
    public void shouldCalculatePercentageForZeroValue() {
        final String percentage = jobStatusPortlet.getPercentage(0, 100);
        assertThat(percentage).isEqualTo("0%");
    }

    @Test
    public void shouldNotFailOnZeroBuilds() {
        final String percentage = jobStatusPortlet.getPercentage(0, 0);
        assertThat(percentage).isEqualTo("0%");
    }

}
