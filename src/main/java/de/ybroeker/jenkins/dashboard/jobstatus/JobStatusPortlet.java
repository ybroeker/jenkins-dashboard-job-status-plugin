package de.ybroeker.jenkins.dashboard.jobstatus;

import java.text.NumberFormat;
import java.util.*;

import javax.annotation.Nonnull;

import hudson.Extension;
import hudson.model.*;
import hudson.plugins.view.dashboard.DashboardPortlet;
import org.kohsuke.stapler.DataBoundConstructor;


public class JobStatusPortlet extends DashboardPortlet {

    private final NumberFormat percentageFormat;

    @DataBoundConstructor
    public JobStatusPortlet(String name) {
        super(name);
        percentageFormat = NumberFormat.getPercentInstance(Locale.ENGLISH);
        percentageFormat.setMinimumFractionDigits(0);
        percentageFormat.setMaximumFractionDigits(2);
    }

    public Map<BallColor, Integer> getBuildStat(List<TopLevelItem> jobs) {
        SortedMap<BallColor, Integer> colStatBuilds = new TreeMap<>();

        for (TopLevelItem job : jobs) {
            if (job instanceof Job) {
                final Run<?, ?> lastBuild = ((Job<?, ?>) job).getLastBuild();
                BallColor bColor = lastBuild.getIconColor();
                if (bColor != null && bColor.noAnime() != null && colStatBuilds.get(bColor) != null) {
                    colStatBuilds.merge(bColor.noAnime(), 1, Integer::sum);
                }
            }
        }
        return colStatBuilds;
    }

    public String getPercentage(int value, int nBuilds) {
        float fraction = nBuilds != 0 ? (((float) value) / nBuilds) : 0;
        return percentageFormat.format(fraction);
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
