![Java Digital Notary](https://github.com/craterdog/java-digital-notary/blob/master/docs/images/seal.jpg)

### Java Digital Notary
The advent of public/private key cryptography has made the digital signing (or notarization)
of electronic documents easy and tamperproof.  The approach is simple, the person or program
acting as a digital notary generates a public/private key pair and keeps the private key
safe.  The public key is wrapped in a self-signed certificate and published in the cloud
so that anyone can download and use it.  This facilitates digital notarization as follows:
 1. The notary creates a digital signature of an electronic document using the private key.
 1. The notary attaches the signature as a notary seal to the document and publishes it.
 1. Anyone who wants to verify the notary seal can download the public certificate and use
it to validate the seal.

The key benefits to this approach include:
 * A notary seal cannot be forged without the private key.
 * The notarized document cannot be changed later without invalidating the notary seal.
 * The notary cannot claim they did not notarize the document (i.e. non-repudiation).

### Highlighted Components
The following highlights the various components that are provided by this project:

 * *NotaryKey* - the private key that is used to notarize a document
 * *NotarySeal* - the tamperproof seal that contains the digital signature of the document
 * *NotaryCerticate* - the public key that is used to validate a notary seal created with
 * *Notarization* - the Java interface that must be supported by all notarization providers
 * *V1NotarizationProvider* - a notarization provider that implements version 1.x of the
notarization protocol

### Quick Links
For more detail on this project click on the following links:

 * [javadocs](http://craterdog.github.io/java-digital-notary/latest/index.html)
 * [wiki](https://github.com/craterdog/java-digital-notary/wiki)
 * [release notes](https://github.com/craterdog/java-digital-notary/wiki/releases)
 * [website](http://craterdog.com)

### Getting Started
To get started using these utilities, include the following dependency in your maven pom.xml file:

```xml
    <dependency>
        <groupId>com.craterdog</groupId>
        <artifactId>java-digital-notary</artifactId>
        <version>x.y</version>
    </dependency>
```

The source code, javadocs and jar file artifacts for this project are available from the
*Maven Central Repository*. If your project doesn't currently use maven and you would like to,
click [here](https://github.com/craterdog/maven-parent-poms) to get started down that path quickly.

### Recognition
*Crater Dog Technologies™* would like to recognize and thank the following
companies for their contributions to the development and testing of various
components within this project:

 * *Blackhawk Network* (http://blackhawknetwork.com)
