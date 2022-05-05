package eu.jrc.vdsd;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class VDSD_ByteStringDecoder 
{


	String rawbarcode;
	byte[] bcodeByte;
	public VDSD_ByteStringDecoder(byte[] _bcodeByte)
	{
		bcodeByte = _bcodeByte;
		rawbarcode = VDSD_Utils.encodeHexString(_bcodeByte);
		rawbarcode = rawbarcode.substring(0, rawbarcode.length()-2);
	}

	VDSD_Barcode decode()
	{
		VDSD_Barcode barcode =  new VDSD_Barcode();
		long startTime = System.nanoTime();
		try {
			barcode.latinBarcode = new String(bcodeByte, StandardCharsets.ISO_8859_1);
			barcode.rawBarcode = rawbarcode;

			int messageStart = 0;

			VDSD_Barcode_Header header = new VDSD_Barcode_Header();
			barcode.header = header;
			VDSD_Barcode_Message message = new VDSD_Barcode_Message();
			barcode.message = message;
			VDSD_Barcode_Signature signature = new VDSD_Barcode_Signature();
			barcode.signature = signature;
			String hexMagicCostant = rawbarcode.substring(messageStart, messageStart + 2);
			String MagicCostant = hexMagicCostant;
			messageStart += 2;
			if (MagicCostant.equalsIgnoreCase("dc")) {
				String hexVersion = rawbarcode.substring(messageStart, messageStart + 2);
				String Version = Integer.toString(Integer.parseInt(hexVersion, 16) + 1);
				messageStart += 2;

				String hexIssuingCountry = rawbarcode.substring(messageStart, messageStart + 4);
				String IssuingCountry = VDSD_C40.decodeAlphanum(hexIssuingCountry);
				messageStart += 4;

				if (!VDSD_Utils.isValidCountry(IssuingCountry))
				{
					barcode.isValid = false;
					barcode.error = "H3, H9";
					return barcode;
				}
				String CertificateAuthorityReference = "";
				String c = new String();
				String cn = new String();
				String serial = new String();

				if (Version.equalsIgnoreCase("3")) {
					String hexCertificateAuthorityReference = rawbarcode.substring(messageStart, messageStart + 12);
					CertificateAuthorityReference = VDSD_C40.decodeAlphanum(hexCertificateAuthorityReference);
					messageStart += 12;

					c = CertificateAuthorityReference.substring(0, 2);
					cn = CertificateAuthorityReference.substring(2, 4);
					serial = CertificateAuthorityReference.substring(4);
					header.realCertificateAuthorityAndReference = c + cn+serial;
				} else if (Version.equalsIgnoreCase("4")) {
					String hexLngtFinder = rawbarcode.substring(messageStart, messageStart + 8);
					String lngt = VDSD_C40.decodeAlphanum(hexLngtFinder).substring(4, 6);

					//I calculate the length of the triplets needed to encode the length of the serial number
					int LngtFinder = Integer.parseInt(String.valueOf((int) (Math.ceil((Integer.parseInt(lngt, 16)) / 3.0)) * 4));

					String hexCertificateAuthorityReference = rawbarcode.substring(messageStart, messageStart + LngtFinder + 8);
					CertificateAuthorityReference = VDSD_C40.decodeAlphanum(hexCertificateAuthorityReference);
					messageStart += LngtFinder + 8;

					c = CertificateAuthorityReference.substring(0, 2);
					cn = CertificateAuthorityReference.substring(2, 4);
					serial = CertificateAuthorityReference.substring(6);

					header.realCertificateAuthorityAndReference = c + cn+serial;
				} else {
					barcode.isValid = false;
					barcode.error = "H2";
					return barcode;
				}

				new BigInteger(serial, 16);

				String hexDocumentIssueDate = rawbarcode.substring(messageStart, messageStart + 6);
				String DocumentIssueDate = VDSD_C40.decodeDecimal(hexDocumentIssueDate);
				if (DocumentIssueDate.length() % 2 == 0) {
					DocumentIssueDate = DocumentIssueDate.substring(2, 4) + "/" + DocumentIssueDate.substring(0, 2) + "/" + DocumentIssueDate.substring(4);
				} else {
					DocumentIssueDate = DocumentIssueDate.substring(1, 3) + "/0" + DocumentIssueDate.substring(0, 1) + "/" + DocumentIssueDate.substring(3);
				}

				if (VDSD_Utils.afterToday(DocumentIssueDate))
				{
					barcode.isValid = false;
					barcode.error = "H4,H8";
					return barcode;
				}

				messageStart += 6;

				String hexSignatureCreationDate = rawbarcode.substring(messageStart, messageStart + 6);
				String SignatureCreationDate = VDSD_C40.decodeDecimal(hexSignatureCreationDate);
				if (SignatureCreationDate.length() % 2 == 0) {
					SignatureCreationDate = SignatureCreationDate.substring(2, 4) + "/" + SignatureCreationDate.substring(0, 2) + "/" + SignatureCreationDate.substring(4);
				} else {
					SignatureCreationDate = SignatureCreationDate.substring(1, 3) + "/0" + SignatureCreationDate.substring(0, 1) + "/" + SignatureCreationDate.substring(3);
				}

				if (VDSD_Utils.afterToday(SignatureCreationDate))
				{
					barcode.isValid = false;
					barcode.error = "H5, H9";
					return barcode;
				}
				messageStart += 6;

				String hexDocumentFeatureDefinitionReference = rawbarcode.substring(messageStart, messageStart + 2);
				String DocumentFeatureDefinitionReference = VDSD_C40.decodeDecimal(hexDocumentFeatureDefinitionReference);
				messageStart += 2;

				if (!DocumentFeatureDefinitionReference.equalsIgnoreCase("93"))
				{
					barcode.isValid = false;
					barcode.error = "H6, H9";
					return barcode;
				}

				String hexDocumentTypeCategory = rawbarcode.substring(messageStart, messageStart + 2);
				String DocumentTypeCategory = VDSD_C40.decodeDecimal(hexDocumentTypeCategory);
				messageStart += 2;

				if (!hexDocumentTypeCategory.equalsIgnoreCase("01"))
				{
					barcode.isValid = false;
					barcode.error = "H7, H9";
					return barcode;
				}

				header.MagicConstant = MagicCostant;
				header.Version = Version;
				header.IssuingCountry = IssuingCountry;
				header.CertificateAuthorityAndReference = CertificateAuthorityReference;
				header.DocumentIssueDate = DocumentIssueDate;
				header.SignatureCreationDate = SignatureCreationDate;
				header.DocumentFeatureDefinitionReference = DocumentFeatureDefinitionReference;
				header.DocumentTypeCategory = DocumentTypeCategory;

				if (header.DocumentTypeCategory.equals("1") && header.DocumentFeatureDefinitionReference.equals(("93")))
					barcode.isSupported = true;
				else if (header.DocumentTypeCategory.equals("3") && header.DocumentFeatureDefinitionReference.equals(("94")))
					barcode.isSupported = true;



				if (barcode.isSupported) {
					int endOfHeaderMessage = 0;
					//READING MESSAGE and SIGNATURE
					String tag = VDSD_C40.decodeDecimal(rawbarcode.substring(36, 38));
					while (messageStart != rawbarcode.length()) {
						tag = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
						messageStart = messageStart + 2;

						//READING MESSAGES
						switch (tag)
						{
						case "1":
						{
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;
							int messageLength = Integer.parseInt(length) * 2;
							if (Integer.parseInt(length) != 48) {
								barcode.isValid = false;
								barcode.error = "M1, M3";
								return barcode;
							}
							String mesg = VDSD_C40.decodeAlphanum(rawbarcode.substring(messageStart, messageStart + messageLength));
							messageStart = messageStart + messageLength;
							if (mesg.length() != 72) {
								barcode.isValid = false;
								barcode.error = "M1, M3";
								return barcode;
							}
							message.MRZ = new VDSD_MRZ(mesg.replaceAll(" ", "<"),44);

							break;
						}
						case "2": {
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;
							int messageLength = Integer.parseInt(length) * 2;

							if (Integer.parseInt(length) != 44)   {
								barcode.isValid = false;
								barcode.error = "M1, M3";
								return barcode;
							}
							String mesg = VDSD_C40.decodeAlphanum(rawbarcode.substring(messageStart, messageStart + messageLength));
							messageStart = messageStart + messageLength;
							if (mesg.length() != 64){
								barcode.isValid = false;
								barcode.error = "M1, M3";
								return barcode;
							}
							message.MRZ = new VDSD_MRZ(mesg.replaceAll(" ", "<"),36);

							break;
						}
						case "3": {
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;
							int messageLength = Integer.parseInt(length) * 2;
							if (messageLength != 2) {
								barcode.isValid = false;
								barcode.error = "M5";
								return barcode;
							}
							String mesg = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + messageLength));
							messageStart = messageStart + messageLength;
							message.nOfEntries = mesg;
							break;
						}
						case "4": {
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;
							int messageLength = Integer.parseInt(length) * 2;
							if (messageLength != 6)
							{
								barcode.isValid = false;
								barcode.error = "M2";
								return barcode;
							}
							String mesg = rawbarcode.substring(messageStart, messageStart + messageLength);
							messageStart = messageStart + messageLength;
							if (Integer.parseInt(length) != 3)
							{
								barcode.isValid = false;
								barcode.error = "M2";
								return barcode;
							}
							message.DurationOfStay = new DurationOfStay(mesg);

							break;
						}
						case "5": {
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;
							if (Integer.parseInt(length) != 6)
							{
								barcode.isValid = false;
								barcode.error = "M4";
								return barcode;
							}
							int messageLength = Integer.parseInt(length) * 2;
							String mesg = VDSD_C40.decodeAlphanum(rawbarcode.substring(messageStart, messageStart + messageLength));
							messageStart = messageStart + messageLength;
							message.PassportNumber = mesg;

							break;
						}
						case "6":{
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;

							int messageLength = Integer.parseInt(length) * 2;
							String mesg = rawbarcode.substring(messageStart, messageStart + messageLength);
							messageStart = messageStart + messageLength;
							message.VisaType = mesg;

							break;
						}
						case "7":{
							String length = VDSD_C40.decodeBinary(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;

							int messageLength = Integer.parseInt(length) * 2;
							String mesg = rawbarcode.substring(messageStart, messageStart + messageLength);
							messageStart = messageStart + messageLength;
							message.Other = mesg;

							break;
						}
						case "255": {
							endOfHeaderMessage = messageStart - 2;
							//READ SIGNATURE
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;

							int signatureLength = Integer.parseInt(length);

							if (messageStart + signatureLength + signatureLength != rawbarcode.length()){
								barcode.isValid = false;
								barcode.error = "SZ2";
								return barcode;
							}

							signature.r = rawbarcode.substring(messageStart, messageStart + signatureLength);
							messageStart += signatureLength;

							signature.s = rawbarcode.substring(messageStart, messageStart + signatureLength);
							messageStart += signatureLength;
							break;

						}
						default:{
							String length = VDSD_C40.decodeDecimal(rawbarcode.substring(messageStart, messageStart + 2));
							messageStart = messageStart + 2;
							int messageLength = Integer.parseInt(length) * 2;
							messageStart = messageStart + messageLength;
							barcode.error = "M9";
							break;

						}
						}
					}




					if (barcode.message.MRZ == null)
					{
						barcode.isValid = false;
						barcode.error = "M6";
						return barcode;
					}
					if (barcode.message.DurationOfStay == null)
					{
						barcode.isValid = false;
						barcode.error = "M7";
						return barcode;
					}
					if (barcode.message.PassportNumber == null)
					{
						barcode.isValid = false;
						barcode.error = "M8";
						return barcode;
					}

					barcode.isValid = true;
					long dbStartTime = System.nanoTime();
					
					VDSD_CRT_Container CRT_HashMap = VDSD_CRT_Container.getIstance();
					VDSD_CRT_Map CRT = CRT_HashMap.getCERTS();
					
					VDSD_CRL_Container CRL_HashMap = VDSD_CRL_Container.getIstance();
					VDSD_CRL_Map CRL = CRL_HashMap.getCRLs();
					
					//NORMAL CODE
					if (barcode.hex_dec == 0)
					{
						//BigInteger bigInt = new BigInteger(serial, 16);
						//serial = bigInt.toString();
						if (header.Version.equalsIgnoreCase("3") && !barcode.header.CertificateAuthorityAndReference.substring(4).equalsIgnoreCase(serial))
						{
							barcode.isValid = false;
							barcode.error = "C1";
							return barcode;
						}

					}
					String certFile = CRT.getCert(c, cn, serial);
					if (header.Version.equalsIgnoreCase("4") && certFile == null)
					{
						barcode.isValid = false;
						barcode.error = "C2";
						return barcode;
					}
					long dbEndTime = System.nanoTime();
					barcode.dbLookUp = (dbEndTime - dbStartTime);
					long certStartTime = System.nanoTime();
					if (certFile != null) 
					{

						VDSD_Cert cert = VDSD_Utils.loadCertificate(certFile);
						String str = cert.x509Cert.getSigAlgName();
						PublicKey publicKey = cert.x509Cert.getPublicKey();
						
						String crl64 =  CRL.getCRL(cert.issuerC, cert.issuerCN);

						if (crl64 != null)
							barcode.isRevoked = VDSD_Utils.loadCRL(crl64).isRevoked(cert.x509Cert);



						barcode.isVerified = VDSD_Utils.isValidSignaturePublicKey(publicKey, barcode.getMessage(endOfHeaderMessage), barcode.signature.toDerFormat());
						if (barcode.isVerified) {
							barcode.certificate.X509 = cert.x509Cert;
						}
						else
						{
							barcode.isValid = false;
							barcode.error = "S1";
							return barcode;
						}

					}
					
					long certEndTime = System.nanoTime();
					barcode.signatureVerification = (certEndTime - certStartTime);

					//END NORMAL CODE

				} else {
					barcode.header = header;
					barcode.message = message;
					barcode.signature = signature;
					barcode.isValid = true;
				}
			} else {
				barcode.isValid = false;
				barcode.error = "H1, H9";
			}
			long endTime = System.nanoTime();
			barcode.duration = (endTime - startTime);
			return barcode;
		}
		catch (Exception e)
		{
			barcode.isValid = false;
			long endTime = System.nanoTime();
			barcode.duration = (endTime - startTime);
			barcode.error = "H10, SZ1";
			return barcode;
		}

		//return barcode;
	}



}
