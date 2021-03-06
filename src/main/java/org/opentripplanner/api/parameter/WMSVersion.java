package org.opentripplanner.api.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WMSVersion extends ArrayList<Integer> {
    private static final Logger LOG = LoggerFactory.getLogger(WMSVersion.class);

    private static final long serialVersionUID = 20120130L; // YYYYMMDD

    public static final List<WMSVersion> supported = Arrays.asList(
                    new WMSVersion(1, 0, 0),
                    new WMSVersion(1, 1, 0),
                    new WMSVersion(1, 1, 1),
                    new WMSVersion(1, 3, 0));
    
    public WMSVersion (String s) {
        super();
        try {
            for (String v : s.split("\\.", 3)) {
                this.add(Integer.parseInt(v));
            }
        } catch (Exception e) {
            LOG.warn("Error parsing WMS version {}", s, e);
            throw new WebApplicationException(Response
                    .status(Status.BAD_REQUEST)
                    .entity("error parsing WMS version: " + e.getMessage())
                    .build());
        }
        if (! supported.contains(this)) {
            throw new WebApplicationException(Response
                    .status(Status.BAD_REQUEST)
                    .entity("WMS version unsupported: " + s)
                    .build());
        }
    }

    private WMSVersion(Integer... ver) {
        for (Integer i : ver)
            this.add(i);
    }
    
}
