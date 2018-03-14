# Garuda

Real time security monitoring

Dynamic security testing made easy. Garuda hooks into any running JVM process and starts monitoring for known security vulnerabilities. It understands things like missing Prepared Statement, parameter validations etc. and logs them as issues.

## Usage

A typical usage for Garuda is in automated security testing. The system under test is launched in a prod like environment with Garuda hooked into monitor security issues. The system under test can then be used manually or through an automated functional test suite. This would generate traffic that would mimic a real user's usage profile which in turn executes all the different code paths under the hood. 

Finally, when the tests are complete, Gardua generates a report on the different vulnerabilities present in the system under test.
