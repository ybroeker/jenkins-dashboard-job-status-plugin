package de.ybroeker.jenkins.dashboard.jobstatus;

import java.util.*;

import hudson.model.BallColor;
import hudson.model.FreeStyleProject;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.assertj.core.api.Assertions.assertThat;


public class JobStatusPortletTest {

    @Rule
    public JenkinsRule j = new JenkinsRule();

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

    @Test
    public void shouldNotFailOnUnbuiltJobs() throws Exception {
        FreeStyleProject project = j.createFreeStyleProject();
        final Map<BallColor, Integer> buildStat = jobStatusPortlet.getBuildStat(Collections.singletonList(project));

        assertThat(buildStat)
                .hasSize(1)
                .containsEntry(BallColor.NOTBUILT, 1)
        ;
    }

    @Test
    public void shouldCalculateCorrectStatsForSIngleBlueRun() throws Exception {
        FreeStyleProject project = j.createFreeStyleProject();
        j.assertBuildStatusSuccess(project.scheduleBuild2(0));
        assertThat(project.getLastBuild()).isNotNull();
        final Map<BallColor, Integer> buildStat = jobStatusPortlet.getBuildStat(Collections.singletonList(project));

        assertThat(buildStat)
                .hasSize(1)
                .containsEntry(BallColor.BLUE, 1)
        ;
    }

    @Test
    public void shouldNotFailOnNullJob() throws Exception {
        final Map<BallColor, Integer> buildStat = jobStatusPortlet.getBuildStat(Collections.singletonList(null));

        assertThat(buildStat)
                .isEmpty()
        ;
    }

}
