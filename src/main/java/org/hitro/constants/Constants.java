package org.hitro.constants;

import lombok.Getter;

public class Constants {
    @Getter
    private static final byte[] commandSeperator = {92,119};

    @Getter
    private static final String pubsubChannelType = "pubsub";

    @Getter
    private static final String pollChannelType = "poll";

    @Getter
    private static final String success = "SUCCESS";

    @Getter
    private static final String failure = "FAILURE";
}
