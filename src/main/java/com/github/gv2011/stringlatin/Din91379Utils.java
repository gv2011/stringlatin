package com.github.gv2011.stringlatin;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.ls.LSInput;
import org.xml.sax.SAXException;


public class Din91379Utils {

	private Din91379Utils() {
		throw new RuntimeException("This is a class without instances.");
	}

	public static void main(final String[] args) {
		final CharacterSet cs = parse(CharacterSet.class);
		cs.getGroups().getGroup().forEach(g->System.out.println(g.getName()));
//		cs.getCharOrSequence().forEach(e->System.out.println(e.getName()));
	}

	private static InputStream getSchemaAsStream(final String name) {
		return Din91379Utils.class.getResourceAsStream("latinchars.xsd");
	}

	public static Schema getSchema(){
		try(InputStream in = getSchemaAsStream("latinchars.xsd")){
			final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			schemaFactory.setResourceResolver(Din91379Utils::resolveResource);
			return schemaFactory.newSchema(new Source[] {new StreamSource(in)});
		} catch (final SAXException|IOException e) {
			throw new RuntimeException("Could not load schema.", e);
		}
	}

	private static LSInput resolveResource(final String type,
        final String namespaceURI,
        final String publicId,
        final String systemId,
        final String baseURI) {
		return new LSInputImp();
	}

	private static <V> V parse(final Class<V> type) {
		try {
			final JAXBContext ctx = JAXBContext.newInstance(ObjectFactory.class);
			final Unmarshaller unmarshaller = ctx.createUnmarshaller();
			unmarshaller.setSchema(getSchema());
			final Object value = requireNonNull(unmarshaller.unmarshal(Din91379Utils.class.getResource("latinchars.xml")));
			return type.cast(value);
		} catch (final JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	private static final class LSInputImp implements LSInput{

		@Override
		public Reader getCharacterStream() {
			return null;
		}

		@Override
		public void setCharacterStream(final Reader characterStream) {
			throw new RuntimeException();

		}

		@Override
		public InputStream getByteStream() {
			return Din91379Utils.class.getResourceAsStream("xml.xsd");
		}

		@Override
		public void setByteStream(final InputStream byteStream) {
			throw new RuntimeException();
		}

		@Override
		public String getStringData() {
			return null;
		}

		@Override
		public void setStringData(final String stringData) {
			throw new RuntimeException();
		}

		@Override
		public String getSystemId() {
			return "xml.xsd";
		}

		@Override
		public void setSystemId(final String systemId) {
			throw new RuntimeException();
		}

		@Override
		public String getPublicId() {
			return null;
		}

		@Override
		public void setPublicId(final String publicId) {
			throw new RuntimeException();
		}

		@Override
		public String getBaseURI() {
			return null;
		}

		@Override
		public void setBaseURI(final String baseURI) {
			throw new RuntimeException();
		}

		@Override
		public String getEncoding() {
			return null;
		}

		@Override
		public void setEncoding(final String encoding) {
			throw new RuntimeException();
		}

		@Override
		public boolean getCertifiedText() {
			throw new RuntimeException();
		}

		@Override
		public void setCertifiedText(final boolean certifiedText) {
			throw new RuntimeException();
		}}

}
