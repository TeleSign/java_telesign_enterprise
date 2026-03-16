4.1.0
- Add remaining Messaging actions.

4.0.1
- Fix endpoint for making requests in ScoreClient

4.0.0
- Added support for Intelligence Cloud to use new endpoint
- Removed Legacy PID Score

3.1.1
- Update SDK install instructions on GitHub to default to latest version of Telesign SDK

3.1.0
- Added full support for App Verify API

3.0.0
- Removed Contact method in PhoneId class.

2.6.1
- Adjust deployment process

2.6.0
- Added methods to get information about a specified phone number.

2.5.0
- Added method to retrieve verification process.
- Added unit test for retrieving verification process.
- Added method to update verification process.
- Added unit test for updating verification process.

2.4.0
- Added tracking to requests.

2.3.1
- Removed Push Verify method in VerifyClient class.

2.3.0
 - Added new method to send requests to Telesign Messaging
 - Added new method to send requests to the Telesign Verify API
 - Added new unit test for requests to Telesign Messaging
 - Added new unit test for requests to the Telesign Verify API

2.2.0
 - AutoVerify rebranded to AppVerify, please update your integration

2.1.1
 - Added completion endpoint to VerifyClient
 - Added console output and verify code input to verify_sms and verify_voice examples
 - Simplified RELEASE.md

2.1.0
 - updated and improved README
 - secret_key refactored to api_key to align with docs and portal
 - api_host is now known as rest_endpoint to align with docs and portal

2.0.0
 - Major refactor and simplification into generic REST client.
 - API parameters are now passed as parans to endpoint handlers.
 - Now using OkHttp as a web client internally which avoids a lot of redundant code.
 - UserAgent is now set to track client usage and help debug issues.
 - generateTelesignHeaders is a static and easily extracted from the SDK if custom behavior/implementation is required.
 - SDK is now targeting Java 8 as Java 7 has reached EoL

0.6.0
 - Added tts_message, sms_message, push_message to smartVerify Api. Also updated Test cases.
 - Added text to speech (TTS) feature of Voice Verify to the Java SDK.
 - Renaming resource variable(Code cleanup)
 - README.rst formatting, punctuation, and link fixes.
 - TMR-141/142 : Changes done for handling changes in AuthMethod enumeration value,  used when signing the Authentication header.
 - Throw IOException for better visibility of underlying subclasses in all APIs.
 
0.5.0
 - Configured Https protocol via constructor parameters
 - Removed string literals from method bodies
 - Capitalized constants
 - Renamed resource variables
 - Moved baseUrl and resource suffixes into static variables

0.4.0
 - Updated Verify Push, Verify Soft Token, Verify Registration, and Smart Verify
 - Set default TLS version to 1.2
 - Overloaded verify status method for backwards compatibility
 - Added debug info to the jar and now generate sources jar during build.
 - Preventing Gson instance from being serialized.
 - Adding verbose junit error output.

0.3.0
 - Adding carrier info to PhoneID Live and Contact responses.

0.2.0
 - Updated comments to PhoneId
 - Removed special characters as it was giving errors while running Junit test
 - Added pom.xml used while migrating to Maven central
 - Use Locale.US for date header
 - Added Originating_ip & Session_id to PhoneId & Verify calls

0.1.0
 - Added connect and read timeouts as configurable parameters
 - Removed redundant 'url.openConnection() & additional timeouts'
 - Added carrier information to PhoneId Standard response
 - Specify UTF-8 encoding in ant build.xml file