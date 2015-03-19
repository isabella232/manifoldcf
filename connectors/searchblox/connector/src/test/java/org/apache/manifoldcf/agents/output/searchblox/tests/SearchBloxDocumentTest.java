package org.apache.manifoldcf.agents.output.searchblox.tests;

import com.google.common.collect.Lists;

import junit.framework.TestCase;

import org.apache.manifoldcf.agents.interfaces.RepositoryDocument;
import org.apache.manifoldcf.core.interfaces.ManifoldCFException;
import org.apache.manifoldcf.agents.output.searchblox.SearchBloxDocument;
import org.apache.manifoldcf.agents.output.searchblox.SearchBloxDocument.DocumentAction;
import org.apache.manifoldcf.agents.output.searchblox.SearchBloxDocument.IndexingFormat;
import org.apache.manifoldcf.agents.output.searchblox.SearchBloxException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alessandro Benedetti
 *         07/03/2015
 *         mcf-searchblox-connector
 */
public class SearchBloxDocumentTest extends TestCase{
    SearchBloxDocument toTest;
    RepositoryDocument rd;

   public void setUp() throws ManifoldCFException {
       Map<String, List<String>> args=initArgs();
       String apikey="apikey";
       rd=initRepoDocument();
       String docURI = "URI";
       toTest=new SearchBloxDocument(apikey, docURI,rd,args);
   }

    private Map<String, List<String>> initArgs() {
        Map<String, List<String>> argMap=new HashMap<String, List<String>>();
        argMap.put("collection", Lists.newArrayList("collection1"));
        argMap.put("title_boost", Lists.newArrayList("1"));
        argMap.put("content_boost", Lists.newArrayList("2"));
        argMap.put("keywords_boost", Lists.newArrayList("3"));
        argMap.put("description_boost", Lists.newArrayList("4"));
        return argMap;

    }

    private RepositoryDocument initRepoDocument() throws ManifoldCFException {
        RepositoryDocument realRepoDoc=new RepositoryDocument();
        String binaryContent="I am the binary content of an Amazing Document";
        InputStream stream = new ByteArrayInputStream(binaryContent.getBytes(StandardCharsets.UTF_8));

        realRepoDoc.addField("title","I am a nice title");
        realRepoDoc.addField("content","I am a nice content in english!");
        realRepoDoc.addField("description","I am a little tiny description");
        realRepoDoc.addField("meta1","I am META1!");
        realRepoDoc.addField("meta2","I am META2!");
        realRepoDoc.setMimeType("html");
        realRepoDoc.setBinary(stream,100);
        realRepoDoc.setCreatedDate(new Date(System.currentTimeMillis()));
        realRepoDoc.setSecurityACL(RepositoryDocument.SECURITY_TYPE_SHARE,new String[]{"user1","user2","user3"});
        realRepoDoc.setSecurityACL(RepositoryDocument.SECURITY_TYPE_DOCUMENT,new String[]{"user12","user22","user33"});
        realRepoDoc.setSecurityDenyACL(RepositoryDocument.SECURITY_TYPE_SHARE, new String[]{"user4", "user5"});
        realRepoDoc.setSecurityDenyACL(RepositoryDocument.SECURITY_TYPE_DOCUMENT, new String[]{"user42", "user52"});
        //allowAttributeName + aclType
        return realRepoDoc;
    }

    @Test
    public void testUpdateXmlString() throws SearchBloxException {
        String xmlGenerated=toTest.toString(IndexingFormat.XML, DocumentAction.ADD_UPDATE);
        System.out.println(xmlGenerated);
        String xmlExpected="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<searchblox apikey=\"apikey\"><document colname=\"collection1\">" +
                "<uid>URI</uid><title boost=\"1\">I am a nice title</title><content boost=\"2\">I am a nice content in english!</content>" +
                "<description boost=\"4\">I am a little tiny description</description><size>100</size><contenttype>html</contenttype>" +
                "<meta name=\"meta2\">I am META2!</meta><meta name=\"share_allow\">user3</meta>" +
                "<meta name=\"share_allow\">user2</meta><meta name=\"share_allow\">user1</meta>" +
                "<meta name=\"meta1\">I am META1!</meta><meta name=\"share_deny\">user4</meta>" +
                "<meta name=\"share_deny\">user5</meta><meta name=\"document_deny\">user52</meta>" +
                "<meta name=\"document_deny\">user42</meta><meta name=\"document_allow\">user22</meta>" +
                "<meta name=\"document_allow\">user12</meta><meta name=\"document_allow\">user33</meta></document></searchblox>";
        assertEquals(xmlExpected,xmlGenerated);
    }

    @Test
    public void testDeleteXmlString() throws SearchBloxException {
        String xmlGenerated=toTest.toString(IndexingFormat.XML, DocumentAction.DELETE);
        System.out.println(xmlGenerated);
        String xmlExpected="<?xml version=\"1.0\" encoding=\"UTF-8\"?><searchblox apikey=\"apikey\"><document colname=\"collection1\" uid=\"URI\"/></searchblox>";
        assertEquals(xmlExpected,xmlGenerated);
    }

}
