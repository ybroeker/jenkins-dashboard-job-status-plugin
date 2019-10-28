package de.ybroeker.jenkins.dashboard.jobstatus;

import java.util.*;

import javax.annotation.Nonnull;

import hudson.Extension;
import hudson.model.*;
import hudson.plugins.view.dashboard.DashboardPortlet;
import org.kohsuke.stapler.DataBoundConstructor;


public class JobStatusPortlet extends DashboardPortlet {

    @DataBoundConstructor
    public JobStatusPortlet(String name) {
        super(name);
    }

    public Map<BallColor, Integer> getBuildStat(List<TopLevelItem> jobs) {
        SortedMap<BallColor, Integer> colStatBuilds = new TreeMap<BallColor, Integer>();
        for (BallColor color : BallColor.values()) {
            colStatBuilds.put(color.noAnime(), 0);
        }
        // loop over jobs
        for (TopLevelItem job : jobs) {
            if (job instanceof Job) {

                final Run<?, ?> lastBuild = ((Job<?, ?>) job).getLastBuild();
                BallColor bColor = lastBuild.getIconColor();
                if (bColor != null && bColor.noAnime() != null && colStatBuilds.get(bColor) != null) {
                    colStatBuilds.put(bColor.noAnime(), colStatBuilds.get(bColor) + 1);
                }
            }
        }
        return colStatBuilds;
    }

    public float roundFloatDecimal(float input) {
        float rounded = (float) Math.round(input * 100f);
        rounded = rounded / 100f;
        return rounded;
    }

    public float getPercentage(int value, int nBuilds) {
        return roundFloatDecimal((((float) value) / nBuilds) * 100);
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<DashboardPortlet> {

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Job Status";
        }

    }

}
