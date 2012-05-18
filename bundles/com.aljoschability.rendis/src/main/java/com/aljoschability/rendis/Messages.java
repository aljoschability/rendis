package com.aljoschability.rendis;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.aljoschability.rendis.messages"; //$NON-NLS-1$
	public static String CircuitBreaker_CreateMulti;
	public static String CircuitBreaker_CreateSingle;

	public static String ChannelPort_Create;

	public static String Counter_CreateMulti;
	public static String Counter_CreateSingle;

	public static String ResidualCurrentProtectiveDevice_CreateMulti;
	public static String ResidualCurrentProtectiveDevice_CreateSingle;

	public static String Wire_Create;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
