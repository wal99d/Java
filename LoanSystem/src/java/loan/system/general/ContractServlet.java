/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.general;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loan.system.entities.EmploymentInfo;
import loan.system.entities.PersonalInfo;
import loan.system.entities.Request;
import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author me
 */
public class ContractServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    private String genderString;
    private String jobString;
    private String statusString;
    private String resedentTypeString;
    
    //Hijri Date to String
    public String toHijriString(Date date){
        //Chronology hijri = IslamicChronology.getInstanceUTC();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
        LocalDate ld= LocalDate.fromDateFields(date);
        
        String tmp = fmt.print(ld);
        
        System.out.println("tmp: "+ tmp);
        return tmp;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            Chronology hijri = IslamicChronology.getInstanceUTC();
            LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
            HttpSession session = request.getSession(false);
            Request r = (Request) session.getAttribute("currentRequest");
            PersonalInfo pi = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                    .setParameter("clientNumber", r.getReqClientId().getClientNumber())
                    .getSingleResult();
            EmploymentInfo ei = emf.createEntityManager().createNamedQuery("EmploymentInfo.findByClientNumber", EmploymentInfo.class)
                    .setParameter("clientNumber", r.getReqClientId().getClientNumber())
                    .getSingleResult();
            
            String g = pi.getGender();
          if(g.equals("1")){
            this.genderString = "ذكر";
          } else if(g.equals("2")){
            this.genderString = "أنثى";
          }

          g = pi.getJob();
          if(g.equals("1")){
              this.jobString = "عسكري";
          }else if(g.equals("2")){
              this.jobString = "مدني";
          }else if(g.equals("3")){
              this.jobString = "أخرى";
          }

          g = pi.getStatus();
          if(g.equals("1")){
              this.statusString = "متزوج";
          }else if(g.equals("2")){
              this.statusString = "أعزب";
          }else if(g.equals("3")){
              this.statusString = "أخرى";
          }

          g = pi.getResedentType();
          if(g.equals("1")){
              this.resedentTypeString = "أملك منزلا أو شقه";
          }else if(g.equals("2")){
              this.resedentTypeString = "مستأجر شقه أو فيلا";
          }else if(g.equals("3")){
              this.resedentTypeString = "متوفر من جهه العمل";
          }else if(g.equals("4")){
              this.resedentTypeString = "أسكن مع أخرين";
          }
            
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment; filename=\"downlaodedFile.doc\"");
            response.setContentType("application/msword;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.write("<html dir=\"rtl\"\n" +
                            "xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                            "xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                            "xmlns:w=\"urn:schemas-microsoft-com:office:word\"\n" +
                            "xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\"\n" +
                            "xmlns:css=\"http://macVmlSchemaUri\" xmlns=\"http://www.w3.org/TR/REC-html40\">\n" +
                            "\n" +
                            "<head>\n" +
                            "<meta name=Title content=\"\">\n" +
                            "<meta name=Keywords content=\"\">\n" +
                            "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n" +
                            "<meta name=ProgId content=Word.Document>\n" +
                            "<meta name=Generator content=\"Microsoft Word 14\">\n" +
                            "<meta name=Originator content=\"Microsoft Word 14\">\n" +
                            "<link rel=File-List href=\"NewContract_files/filelist.xml\">\n" +
                            "<link rel=Edit-Time-Data href=\"NewContract_files/editdata.mso\">\n" +
                            "<!--[if !mso]>\n" +
                            "<style>\n" +
                            "v\\:* {behavior:url(#default#VML);}\n" +
                            "o\\:* {behavior:url(#default#VML);}\n" +
                            "w\\:* {behavior:url(#default#VML);}\n" +
                            ".shape {behavior:url(#default#VML);}\n" +
                            "</style>\n" +
                            "<![endif]--><!--[if gte mso 9]><xml>\n" +
                            " <o:DocumentProperties>\n" +
                            "  <o:Author>Admin</o:Author>\n" +
                            "  <o:LastAuthor>Waleed ALHarthi</o:LastAuthor>\n" +
                            "  <o:Revision>2</o:Revision>\n" +
                            "  <o:TotalTime>56</o:TotalTime>\n" +
                            "  <o:LastPrinted>2013-08-04T19:24:00Z</o:LastPrinted>\n" +
                            "  <o:Created>2013-12-24T19:24:00Z</o:Created>\n" +
                            "  <o:LastSaved>2013-12-24T19:24:00Z</o:LastSaved>\n" +
                            "  <o:Pages>2</o:Pages>\n" +
                            "  <o:Words>565</o:Words>\n" +
                            "  <o:Characters>3227</o:Characters>\n" +
                            "  <o:Lines>26</o:Lines>\n");
                out.write("  <o:Paragraphs>7</o:Paragraphs>\n" +
                            "  <o:CharactersWithSpaces>3785</o:CharactersWithSpaces>\n" +
                            "  <o:Version>14.0</o:Version>\n" +
                            " </o:DocumentProperties>\n" +
                            " <o:OfficeDocumentSettings>\n" +
                            "  <o:RelyOnVML/>\n" +
                            "  <o:AllowPNG/>\n" +
                            "  <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                            "  <o:TargetScreenSize>800x600</o:TargetScreenSize>\n" +
                            " </o:OfficeDocumentSettings>\n" +
                            "</xml><![endif]-->\n" +
                            "<link rel=themeData href=\"NewContract_files/themedata.xml\">\n" +
                            "<!--[if gte mso 9]><xml>\n" +
                            " <w:WordDocument>\n" +
                            "  <w:TrackMoves>false</w:TrackMoves>\n" +
                            "  <w:TrackFormatting/>\n" +
                            "  <w:PunctuationKerning/>\n" +
                            "  <w:DrawingGridHorizontalSpacing>5.5 pt</w:DrawingGridHorizontalSpacing>\n" +
                            "  <w:DisplayHorizontalDrawingGridEvery>2</w:DisplayHorizontalDrawingGridEvery>\n" +
                            "  <w:ValidateAgainstSchemas/>\n" +
                            "  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>\n" +
                            "  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>\n" +
                            "  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>\n" +
                            "  <w:DoNotPromoteQF/>\n" +
                            "  <w:LidThemeOther>EN-US</w:LidThemeOther>\n" +
                            "  <w:LidThemeAsian>JA</w:LidThemeAsian>\n" +
                            "  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>\n" +
                            "  <w:Compatibility>\n" +
                            "   <w:BreakWrappedTables/>\n" +
                            "   <w:SnapToGridInCell/>\n" +
                            "   <w:WrapTextWithPunct/>\n" +
                            "   <w:UseAsianBreakRules/>\n" +
                            "   <w:DontGrowAutofit/>\n" +
                            "   <w:DontUseIndentAsNumberingTabStop/>\n" +
                            "   <w:FELineBreak11/>\n" +
                            "   <w:WW11IndentRules/>\n" +
                            "   <w:DontAutofitConstrainedTables/>\n" +
                            "   <w:AutofitLikeWW11/>\n" +
                            "   <w:HangulWidthLikeWW11/>\n" +
                            "   <w:UseNormalStyleForList/>\n" +
                            "   <w:DontVertAlignCellWithSp/>\n" +
                            "   <w:DontBreakConstrainedForcedTables/>\n" +
                            "   <w:DontVertAlignInTxbx/>\n" +
                            "   <w:Word11KerningPairs/>\n" +
                            "   <w:CachedColBalance/>\n" +
                            "  </w:Compatibility>\n" +
                            "  <m:mathPr>\n" +
                            "   <m:mathFont m:val=\"Cambria Math\"/>\n" +
                            "   <m:brkBin m:val=\"before\"/>\n" +
                            "   <m:brkBinSub m:val=\"&#45;-\"/>\n" +
                            "   <m:smallFrac m:val=\"off\"/>\n" +
                            "   <m:dispDef/>\n" +
                            "   <m:lMargin m:val=\"0\"/>\n" +
                            "   <m:rMargin m:val=\"0\"/>\n" +
                            "   <m:defJc m:val=\"centerGroup\"/>\n" +
                            "   <m:wrapIndent m:val=\"1440\"/>\n" +
                            "   <m:intLim m:val=\"subSup\"/>\n" );
                  out.write("   <m:naryLim m:val=\"undOvr\"/>\n" +
                            "  </m:mathPr></w:WordDocument>\n" +
                            "</xml><![endif]--><!--[if gte mso 9]><xml>\n" +
                            " <w:LatentStyles DefLockedState=\"false\" DefUnhideWhenUsed=\"true\"\n" +
                            "  DefSemiHidden=\"true\" DefQFormat=\"false\" DefPriority=\"99\"\n" +
                            "  LatentStyleCount=\"276\">\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"0\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Normal\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"heading 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 7\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 8\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 9\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 7\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 8\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 9\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"35\" QFormat=\"true\" Name=\"caption\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"10\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Title\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"1\" Name=\"Default Paragraph Font\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"11\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Subtitle\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"22\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Strong\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"20\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Emphasis\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"59\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Table Grid\"/>\n" +
                            "  <w:LsdException Locked=\"false\" UnhideWhenUsed=\"false\" Name=\"Note Level 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"1\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Note Level 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 7\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 8\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Note Level 9\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Placeholder Text\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"No Spacing\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Shading\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Dark List\"/>\n" +
                            "  <w:LsdException Locked=\"false\" UnhideWhenUsed=\"false\" Name=\"Colorful Shading\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"34\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Colorful List\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"29\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Colorful Grid\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"30\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Light Shading Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Revision\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"List Paragraph\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Quote\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Intense Quote\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 1\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n");
                out.write(  "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 2\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 3\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 4\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 5\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 6\"/>\n" );
                out.write(  "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"19\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Medium List 1 Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"21\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Medium List 2 Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"31\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Medium Grid 1 Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"32\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Medium Grid 2 Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"33\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Medium Grid 3 Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"37\" Name=\"Dark List Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" QFormat=\"true\"\n" +
                            "   Name=\"Colorful Shading Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 6\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"19\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Subtle Emphasis\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"21\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Intense Emphasis\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"31\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Subtle Reference\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"32\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Intense Reference\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"33\" SemiHidden=\"false\"\n" +
                            "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Book Title\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"37\" Name=\"Bibliography\"/>\n" +
                            "  <w:LsdException Locked=\"false\" Priority=\"39\" QFormat=\"true\" Name=\"TOC Heading\"/>\n" +
                            " </w:LatentStyles>\n" +
                            "</xml><![endif]-->\n" +
                            "<style>\n" +
                            "<!--\n" +
                            " /* Font Definitions */\n" +
                            "@font-face\n" +
                            "	{font-family:Arial;\n" +
                            "	panose-1:2 11 6 4 2 2 2 2 2 4;\n" +
                            "	mso-font-charset:0;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:-536859905 -1073711037 9 0 511 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:\"Courier New\";\n" +
                            "	panose-1:2 7 3 9 2 2 5 2 4 4;\n" +
                            "	mso-font-charset:0;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:-536859905 -1073711037 9 0 511 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:Wingdings;\n" +
                            "	panose-1:5 0 0 0 0 0 0 0 0 0;\n" +
                            "	mso-font-charset:2;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:0 268435456 0 0 -2147483648 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:Wingdings;\n" +
                            "	panose-1:5 0 0 0 0 0 0 0 0 0;\n" +
                            "	mso-font-charset:2;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:0 268435456 0 0 -2147483648 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:Calibri;\n" +
                            "	panose-1:2 15 5 2 2 2 4 3 2 4;\n" +
                            "	mso-font-charset:0;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:-520092929 1073786111 9 0 415 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:Cambria;\n" +
                            "	panose-1:2 4 5 3 5 4 6 3 2 4;\n" +
                            "	mso-font-charset:0;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:-536870145 1073743103 0 0 415 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:\"DecoType Thuluth\";\n" +
                            "	mso-font-alt:\"Menlo Bold\";\n" +
                            "	mso-font-charset:178;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:8193 -2147483648 8 0 64 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:\"DecoType Naskh Variants\";\n" +
                            "	mso-font-alt:\"Optima ExtraBlack\";\n" +
                            "	mso-font-charset:178;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:8193 -2147483648 8 0 64 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:\"PT Bold Heading\";\n" +
                            "	mso-font-alt:\"Optima ExtraBlack\";\n" +
                            "	mso-font-charset:178;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:8193 -2147483648 8 0 64 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:\"Traditional Arabic\";\n" +
                            "	mso-font-alt:\"Times New Roman\";\n" +
                            "	mso-font-charset:0;\n" +
                            "	mso-generic-font-family:roman;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:8195 -2147483648 8 0 65 0;}\n" +
                            "@font-face\n" +
                            "	{font-family:Tahoma;\n" +
                            "	panose-1:2 11 6 4 3 5 4 4 2 4;\n" +
                            "	mso-font-charset:0;\n" +
                            "	mso-generic-font-family:auto;\n" +
                            "	mso-font-pitch:variable;\n" +
                            "	mso-font-signature:-520082689 -1073717157 41 0 66047 0;}\n" +
                            " /* Style Definitions */\n" );
                out.write(  "p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
                            "	{mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-parent:\"\";\n" +
                            "	margin-top:0in;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:10.0pt;\n" +
                            "	margin-left:0in;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "h1\n" +
                            "	{mso-style-priority:9;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-link:\"Heading 1 Char\";\n" +
                            "	mso-style-next:Normal;\n" +
                            "	margin-top:24.0pt;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan lines-together;\n" +
                            "	page-break-after:avoid;\n" +
                            "	mso-outline-level:1;\n" +
                            "	font-size:14.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#365F91;\n" +
                            "	mso-font-kerning:0pt;}\n" +
                            "h2\n" +
                            "	{mso-style-priority:9;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-link:\"Heading 2 Char\";\n" +
                            "	mso-style-next:Normal;\n" +
                            "	margin-top:10.0pt;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan lines-together;\n" +
                            "	page-break-after:avoid;\n" +
                            "	mso-outline-level:2;\n" +
                            "	font-size:13.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#4F81BD;}\n" +
                            "h3\n" +
                            "	{mso-style-priority:9;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-link:\"Heading 3 Char\";\n" +
                            "	mso-style-next:Normal;\n" +
                            "	margin-top:10.0pt;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan lines-together;\n" +
                            "	page-break-after:avoid;\n" +
                            "	mso-outline-level:3;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#4F81BD;}\n" +
                            "h4\n" +
                            "	{mso-style-priority:9;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-link:\"Heading 4 Char\";\n" +
                            "	mso-style-next:Normal;\n" +
                            "	margin-top:10.0pt;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan lines-together;\n" +
                            "	page-break-after:avoid;\n" +
                            "	mso-outline-level:4;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#4F81BD;\n" +
                            "	font-style:italic;}\n" +
                            "h5\n" +
                            "	{mso-style-priority:9;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-link:\"Heading 5 Char\";\n" +
                            "	mso-style-next:Normal;\n" +
                            "	margin-top:10.0pt;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan lines-together;\n" +
                            "	page-break-after:avoid;\n" +
                            "	mso-outline-level:5;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#243F60;\n" +
                            "	font-weight:normal;}\n" +
                            "p.MsoHeader, li.MsoHeader, div.MsoHeader\n" +
                            "	{mso-style-priority:99;\n" +
                            "	mso-style-link:\"Header Char\";\n" +
                            "	margin:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	tab-stops:center 207.65pt right 415.3pt;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "p.MsoFooter, li.MsoFooter, div.MsoFooter\n" +
                            "	{mso-style-priority:99;\n" +
                            "	mso-style-link:\"Footer Char\";\n" +
                            "	margin:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	tab-stops:center 207.65pt right 415.3pt;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "p.MsoAcetate, li.MsoAcetate, div.MsoAcetate\n" +
                            "	{mso-style-noshow:yes;\n" +
                            "	mso-style-priority:99;\n" +
                            "	mso-style-link:\"Balloon Text Char\";\n" +
                            "	margin:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:8.0pt;\n" +
                            "	font-family:Tahoma;\n" +
                            "	mso-fareast-font-family:Calibri;}\n" +
                            "p.MsoNoSpacing, li.MsoNoSpacing, div.MsoNoSpacing\n" +
                            "	{mso-style-priority:1;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-parent:\"\";\n" +
                            "	margin:0in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	text-align:right;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph\n" +
                            "	{mso-style-priority:34;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	margin-top:0in;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:10.0pt;\n" +
                            "	margin-left:.5in;\n" +
                            "	mso-add-space:auto;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst\n" +
                            "	{mso-style-priority:34;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-type:export-only;\n" +
                            "	margin-top:0in;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:.5in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	mso-add-space:auto;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan;\n" );
                  out.write("	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle\n" +
                            "	{mso-style-priority:34;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-type:export-only;\n" +
                            "	margin-top:0in;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:0in;\n" +
                            "	margin-left:.5in;\n" +
                            "	margin-bottom:.0001pt;\n" +
                            "	mso-add-space:auto;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast\n" +
                            "	{mso-style-priority:34;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-type:export-only;\n" +
                            "	margin-top:0in;\n" +
                            "	margin-right:0in;\n" +
                            "	margin-bottom:10.0pt;\n" +
                            "	margin-left:.5in;\n" +
                            "	mso-add-space:auto;\n" +
                            "	text-align:right;\n" +
                            "	line-height:115%;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:11.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            "span.Heading1Char\n" +
                            "	{mso-style-name:\"Heading 1 Char\";\n" +
                            "	mso-style-priority:9;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:\"Heading 1\";\n" +
                            "	mso-ansi-font-size:14.0pt;\n" +
                            "	mso-bidi-font-size:14.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-ascii-font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-hansi-font-family:Cambria;\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#365F91;\n" +
                            "	font-weight:bold;}\n" +
                            "span.Heading2Char\n" +
                            "	{mso-style-name:\"Heading 2 Char\";\n" +
                            "	mso-style-priority:9;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:\"Heading 2\";\n" +
                            "	mso-ansi-font-size:13.0pt;\n" +
                            "	mso-bidi-font-size:13.0pt;\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-ascii-font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-hansi-font-family:Cambria;\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#4F81BD;\n" +
                            "	font-weight:bold;}\n" +
                            "span.Heading3Char\n" +
                            "	{mso-style-name:\"Heading 3 Char\";\n" +
                            "	mso-style-priority:9;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:\"Heading 3\";\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-ascii-font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-hansi-font-family:Cambria;\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#4F81BD;\n" +
                            "	font-weight:bold;}\n" +
                            "span.Heading4Char\n" +
                            "	{mso-style-name:\"Heading 4 Char\";\n" +
                            "	mso-style-priority:9;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:\"Heading 4\";\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-ascii-font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-hansi-font-family:Cambria;\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#4F81BD;\n" +
                            "	font-weight:bold;\n" +
                            "	font-style:italic;}\n" +
                            "span.Heading5Char\n" +
                            "	{mso-style-name:\"Heading 5 Char\";\n" +
                            "	mso-style-priority:9;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:\"Heading 5\";\n" +
                            "	font-family:Cambria;\n" +
                            "	mso-ascii-font-family:Cambria;\n" +
                            "	mso-fareast-font-family:\"Times New Roman\";\n" +
                            "	mso-hansi-font-family:Cambria;\n" +
                            "	mso-bidi-font-family:\"Times New Roman\";\n" +
                            "	color:#243F60;}\n" +
                            "span.HeaderChar\n" +
                            "	{mso-style-name:\"Header Char\";\n" +
                            "	mso-style-priority:99;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:Header;}\n" +
                            "span.FooterChar\n" +
                            "	{mso-style-name:\"Footer Char\";\n" +
                            "	mso-style-priority:99;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:Footer;}\n" +
                            "span.BalloonTextChar\n" +
                            "	{mso-style-name:\"Balloon Text Char\";\n" +
                            "	mso-style-noshow:yes;\n" +
                            "	mso-style-priority:99;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-style-locked:yes;\n" +
                            "	mso-style-link:\"Balloon Text\";\n" +
                            "	mso-ansi-font-size:8.0pt;\n" +
                            "	mso-bidi-font-size:8.0pt;\n" +
                            "	font-family:Tahoma;\n" +
                            "	mso-ascii-font-family:Tahoma;\n" +
                            "	mso-hansi-font-family:Tahoma;\n" +
                            "	mso-bidi-font-family:Tahoma;}\n" +
                            ".MsoChpDefault\n" +
                            "	{mso-style-type:export-only;\n" +
                            "	mso-default-props:yes;\n" +
                            "	mso-ascii-font-family:Calibri;\n" +
                            "	mso-fareast-font-family:Calibri;\n" +
                            "	mso-hansi-font-family:Calibri;\n" +
                            "	mso-bidi-font-family:Arial;}\n" +
                            " /* Page Definitions */\n" +
                            "@page\n" +
                            "	{mso-footnote-separator:url(\":NewContract_files:header.htm\") fs;\n" +
                            "	mso-footnote-continuation-separator:url(\":NewContract_files:header.htm\") fcs;\n" +
                            "	mso-endnote-separator:url(\":NewContract_files:header.htm\") es;\n" +
                            "	mso-endnote-continuation-separator:url(\":NewContract_files:header.htm\") ecs;}\n" +
                            "@page WordSection1\n" +
                            "	{size:595.3pt 841.9pt;\n" +
                            "	margin:.5in .5in .5in .5in;\n" +
                            "	mso-header-margin:35.45pt;\n" +
                            "	mso-footer-margin:35.45pt;\n" +
                            "	mso-page-numbers-chapter-style:header-1;\n" +
                            "	mso-even-header:url(\":NewContract_files:header.htm\") eh1;\n" +
                            "	mso-header:url(\":NewContract_files:header.htm\") h1;\n" +
                            "	mso-footer:url(\":NewContract_files:header.htm\") f1;\n" +
                            "	mso-first-header:url(\":NewContract_files:header.htm\") fh1;\n" +
                            "	mso-paper-source:0;\n" +
                            "	mso-gutter-direction:rtl;}\n" +
                            "div.WordSection1\n" +
                            "	{page:WordSection1;}\n" +
                            " /* List Definitions */\n" +
                            "@list l0\n" +
                            "	{mso-list-id:-227;\n" +
                            "	mso-list-template-ids:-1510201396;}\n" +
                            "@list l0:level1\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:\"\";\n" +
                            "	mso-level-tab-stop:0in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:0in;\n" +
                            "	text-indent:0in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l0:level2\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:.5in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:.75in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l0:level3\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:1.0in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:1.25in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l0:level4\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:1.5in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:1.75in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l0:level5\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:2.0in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:2.25in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l0:level6\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:2.5in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:2.75in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l0:level7\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:3.0in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:3.25in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l0:level8\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:3.5in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:3.75in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l0:level9\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:4.0in;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:4.25in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l1\n" +
                            "	{mso-list-id:220291715;\n");
                out.write(  "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:457080414 67698689 67698691 67698693 67698689 67698691 67698693 67698689 67698691 67698693;}\n" +
                            "@list l1:level1\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:3.5in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l1:level2\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:4.0in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l1:level3\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:4.5in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l1:level4\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:5.0in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l1:level5\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:5.5in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l1:level6\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:6.0in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l1:level7\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:6.5in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l1:level8\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:7.0in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l1:level9\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:7.5in;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l2\n" +
                            "	{mso-list-id:1121073560;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:693275832 1755627326 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}\n" +
                            "@list l2:level1\n" +
                            "	{mso-level-text:%1-;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:84.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l2:level2\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:120.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l2:level3\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:156.2pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l2:level4\n" +
                            "	{mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:192.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l2:level5\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:228.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l2:level6\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:264.2pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l2:level7\n" +
                            "	{mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:300.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l2:level8\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:336.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l2:level9\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:372.2pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l3\n" +
                            "	{mso-list-id:1124807739;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:31868366 67698689 67698691 67698693 67698689 67698691 67698693 67698689 67698691 67698693;}\n" +
                            "@list l3:level1\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:40.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l3:level2\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:76.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l3:level3\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:112.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l3:level4\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:148.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l3:level5\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:184.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l3:level6\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:220.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l3:level7\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:256.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l3:level8\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:292.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l3:level9\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:328.15pt;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l4\n" +
                            "	{mso-list-id:1155075462;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:-990769344 -1456016554 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}\n" +
                            "@list l4:level1\n" +
                            "	{mso-level-tab-stop:68.1pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:68.1pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l4:level2\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:104.1pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:104.1pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l4:level3\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:140.1pt;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:140.1pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l4:level4\n" +
                            "	{mso-level-tab-stop:176.1pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:176.1pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l4:level5\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:212.1pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:212.1pt;\n" +
                            "	text-indent:-.25in;}\n" );
                  out.write("@list l4:level6\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:248.1pt;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:248.1pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l4:level7\n" +
                            "	{mso-level-tab-stop:284.1pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:284.1pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l4:level8\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:320.1pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:320.1pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l4:level9\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:356.1pt;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:356.1pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l5\n" +
                            "	{mso-list-id:1250120952;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:1903488952 -905130390 67698691 67698693 67698689 67698691 67698693 67698689 67698691 67698693;}\n" +
                            "@list l5:level1\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:-;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Times New Roman\";\n" +
                            "	mso-fareast-font-family:Calibri;}\n" +
                            "@list l5:level2\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l5:level3\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l5:level4\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l5:level5\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l5:level6\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l5:level7\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l5:level8\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l5:level9\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l6\n" +
                            "	{mso-list-id:1758088840;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:-1793029164 67698689 67698691 67698693 67698689 67698691 67698693 67698689 67698691 67698693;}\n" +
                            "@list l6:level1\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l6:level2\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l6:level3\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l6:level4\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l6:level5\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l6:level6\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l6:level7\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Symbol;}\n" +
                            "@list l6:level8\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:o;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:\"Courier New\";}\n" +
                            "@list l6:level9\n" +
                            "	{mso-level-number-format:bullet;\n" +
                            "	mso-level-text:;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	text-indent:-.25in;\n" +
                            "	font-family:Wingdings;}\n" +
                            "@list l7\n" +
                            "	{mso-list-id:1854151487;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:693275832 1755627326 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}\n" +
                            "@list l7:level1\n" +
                            "	{mso-level-text:%1-;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:84.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l7:level2\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:120.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l7:level3\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:156.2pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l7:level4\n" +
                            "	{mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:192.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l7:level5\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:228.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l7:level6\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:264.2pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l7:level7\n" +
                            "	{mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:300.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l7:level8\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:336.2pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l7:level9\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:none;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:372.2pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l8\n" +
                            "	{mso-list-id:2067678934;\n" +
                            "	mso-list-type:hybrid;\n" +
                            "	mso-list-template-ids:2139770260 -1940509480 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}\n" +
                            "@list l8:level1\n" +
                            "	{mso-level-text:%1-;\n" +
                            "	mso-level-tab-stop:25.5pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:48.2pt;\n" +
                            "	text-indent:-19.85pt;}\n" +
                            "@list l8:level2\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:75.75pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:75.75pt;\n" +
                            "	text-indent:-.25in;}\n" );
                  out.write("@list l8:level3\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:111.75pt;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:111.75pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l8:level4\n" +
                            "	{mso-level-tab-stop:147.75pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:147.75pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l8:level5\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:183.75pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:183.75pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l8:level6\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:219.75pt;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:219.75pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "@list l8:level7\n" +
                            "	{mso-level-tab-stop:255.75pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:255.75pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l8:level8\n" +
                            "	{mso-level-number-format:alpha-lower;\n" +
                            "	mso-level-tab-stop:291.75pt;\n" +
                            "	mso-level-number-position:left;\n" +
                            "	margin-left:291.75pt;\n" +
                            "	text-indent:-.25in;}\n" +
                            "@list l8:level9\n" +
                            "	{mso-level-number-format:roman-lower;\n" +
                            "	mso-level-tab-stop:327.75pt;\n" +
                            "	mso-level-number-position:right;\n" +
                            "	margin-left:327.75pt;\n" +
                            "	text-indent:-9.0pt;}\n" +
                            "ol\n" +
                            "	{margin-bottom:0in;}\n" +
                            "ul\n" +
                            "	{margin-bottom:0in;}\n" +
                            "-->\n" +
                            "</style>\n" +
                            "<!--[if gte mso 10]>\n" +
                            "<style>\n" +
                            " /* Style Definitions */\n" +
                            "table.MsoNormalTable\n" +
                            "	{mso-style-name:\"Table Normal\";\n" +
                            "	mso-tstyle-rowband-size:0;\n" +
                            "	mso-tstyle-colband-size:0;\n" +
                            "	mso-style-noshow:yes;\n" +
                            "	mso-style-priority:99;\n" +
                            "	mso-style-qformat:yes;\n" +
                            "	mso-style-parent:\"\";\n" +
                            "	mso-padding-alt:0in 5.4pt 0in 5.4pt;\n" +
                            "	mso-para-margin:0in;\n" +
                            "	mso-para-margin-bottom:.0001pt;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:10.0pt;\n" +
                            "	font-family:Calibri;}\n" +
                            "table.MsoTableGrid\n" +
                            "	{mso-style-name:\"Table Grid\";\n" +
                            "	mso-tstyle-rowband-size:0;\n" +
                            "	mso-tstyle-colband-size:0;\n" +
                            "	mso-style-priority:59;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	border:solid windowtext 1.0pt;\n" +
                            "	mso-border-alt:solid windowtext .5pt;\n" +
                            "	mso-padding-alt:0in 5.4pt 0in 5.4pt;\n" +
                            "	mso-border-insideh:.5pt solid windowtext;\n" +
                            "	mso-border-insidev:.5pt solid windowtext;\n" +
                            "	mso-para-margin:0in;\n" +
                            "	mso-para-margin-bottom:.0001pt;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:10.0pt;\n" +
                            "	font-family:Calibri;}\n" +
                            "table.MsoTableMediumGrid3Accent6\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-tstyle-rowband-size:1;\n" +
                            "	mso-tstyle-colband-size:1;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	border:solid white 1.0pt;\n" +
                            "	mso-padding-alt:0in 5.4pt 0in 5.4pt;\n" +
                            "	mso-border-insideh:.75pt solid white;\n" +
                            "	mso-border-insidev:.75pt solid white;\n" +
                            "	mso-tstyle-shading:#FDE4D0;\n" +
                            "	mso-para-margin:0in;\n" +
                            "	mso-para-margin-bottom:.0001pt;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:10.0pt;\n" +
                            "	font-family:Calibri;}\n" +
                            "table.MsoTableMediumGrid3Accent6FirstRow\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-table-condition:first-row;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#F79646;\n" +
                            "	mso-tstyle-border-top:1.0pt solid white;\n" +
                            "	mso-tstyle-border-left:1.0pt solid white;\n" +
                            "	mso-tstyle-border-bottom:3.0pt solid white;\n" +
                            "	mso-tstyle-border-right:1.0pt solid white;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:1.0pt solid white;\n" +
                            "	color:white;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;\n" +
                            "	mso-ansi-font-style:normal;\n" +
                            "	mso-bidi-font-style:normal;}\n" +
                            "table.MsoTableMediumGrid3Accent6LastRow\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-table-condition:last-row;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#F79646;\n" +
                            "	mso-tstyle-border-top:3.0pt solid white;\n" +
                            "	mso-tstyle-border-left:1.0pt solid white;\n" +
                            "	mso-tstyle-border-bottom:1.0pt solid white;\n" +
                            "	mso-tstyle-border-right:1.0pt solid white;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:1.0pt solid white;\n" +
                            "	color:white;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;\n" +
                            "	mso-ansi-font-style:normal;\n" +
                            "	mso-bidi-font-style:normal;}\n" +
                            "table.MsoTableMediumGrid3Accent6FirstCol\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-table-condition:first-column;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#F79646;\n" +
                            "	mso-tstyle-border-left:1.0pt solid white;\n" +
                            "	mso-tstyle-border-right:3.0pt solid white;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;\n" +
                            "	color:white;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;\n" +
                            "	mso-ansi-font-style:normal;\n" +
                            "	mso-bidi-font-style:normal;}\n" +
                            "table.MsoTableMediumGrid3Accent6LastCol\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-table-condition:last-column;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#F79646;\n" +
                            "	mso-tstyle-border-top:cell-none;\n" +
                            "	mso-tstyle-border-left:3.0pt solid white;\n" +
                            "	mso-tstyle-border-bottom:cell-none;\n" +
                            "	mso-tstyle-border-right:cell-none;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;\n" +
                            "	color:white;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;\n" +
                            "	mso-ansi-font-style:normal;\n" +
                            "	mso-bidi-font-style:normal;}\n" +
                            "table.MsoTableMediumGrid3Accent6OddColumn\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-table-condition:odd-column;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#FBCAA2;\n" +
                            "	mso-tstyle-border-top:1.0pt solid white;\n" +
                            "	mso-tstyle-border-left:1.0pt solid white;\n" +
                            "	mso-tstyle-border-bottom:1.0pt solid white;\n" +
                            "	mso-tstyle-border-right:1.0pt solid white;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;}\n" +
                            "table.MsoTableMediumGrid3Accent6OddRow\n" +
                            "	{mso-style-name:\"Medium Grid 3 - Accent 6\";\n" +
                            "	mso-table-condition:odd-row;\n" +
                            "	mso-style-priority:69;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#FBCAA2;\n" +
                            "	mso-tstyle-border-top:1.0pt solid white;\n" +
                            "	mso-tstyle-border-left:1.0pt solid white;\n" +
                            "	mso-tstyle-border-bottom:1.0pt solid white;\n" +
                            "	mso-tstyle-border-right:1.0pt solid white;\n" +
                            "	mso-tstyle-border-insideh:1.0pt solid white;\n" +
                            "	mso-tstyle-border-insidev:1.0pt solid white;}\n" +
                            "table.-11\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-tstyle-rowband-size:1;\n" +
                            "	mso-tstyle-colband-size:1;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	border-top:solid #4F81BD 1.0pt;\n" +
                            "	border-left:none;\n" +
                            "	border-bottom:solid #4F81BD 1.0pt;\n" +
                            "	border-right:none;\n" +
                            "	mso-padding-alt:0in 5.4pt 0in 5.4pt;\n" +
                            "	mso-para-margin:0in;\n" +
                            "	mso-para-margin-bottom:.0001pt;\n" +
                            "	mso-pagination:widow-orphan;\n" +
                            "	font-size:10.0pt;\n" +
                            "	font-family:Calibri;\n" +
                            "	color:#365F91;}\n" +
                            "table.-11FirstRow\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-table-condition:first-row;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-border-top:1.0pt solid #4F81BD;\n" +
                            "	mso-tstyle-border-left:cell-none;\n" +
                            "	mso-tstyle-border-bottom:1.0pt solid #4F81BD;\n" +
                            "	mso-tstyle-border-right:cell-none;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;\n" +
                            "	mso-para-margin-top:0in;\n" +
                            "	mso-para-margin-bottom:0in;\n" +
                            "	mso-para-margin-bottom:.0001pt;\n" +
                            "	line-height:normal;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;}\n" +
                            "table.-11LastRow\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-table-condition:last-row;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-border-top:1.0pt solid #4F81BD;\n" +
                            "	mso-tstyle-border-left:cell-none;\n" +
                            "	mso-tstyle-border-bottom:1.0pt solid #4F81BD;\n" +
                            "	mso-tstyle-border-right:cell-none;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;\n" +
                            "	mso-para-margin-top:0in;\n" +
                            "	mso-para-margin-bottom:0in;\n" +
                            "	mso-para-margin-bottom:.0001pt;\n" +
                            "	line-height:normal;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;}\n" +
                            "table.-11FirstCol\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-table-condition:first-column;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;}\n" +
                            "table.-11LastCol\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-table-condition:last-column;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-ansi-font-weight:bold;\n" +
                            "	mso-bidi-font-weight:bold;}\n" +
                            "table.-11OddColumn\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-table-condition:odd-column;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#D3DFEE;\n" +
                            "	mso-tstyle-border-left:cell-none;\n" +
                            "	mso-tstyle-border-right:cell-none;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;}\n" +
                            "table.-11OddRow\n" +
                            "	{mso-style-name:\"تظليل فاتح - تمييز 11\";\n" +
                            "	mso-table-condition:odd-row;\n" +
                            "	mso-style-priority:60;\n" +
                            "	mso-style-unhide:no;\n" +
                            "	mso-tstyle-shading:#D3DFEE;\n" +
                            "	mso-tstyle-border-left:cell-none;\n" +
                            "	mso-tstyle-border-right:cell-none;\n" +
                            "	mso-tstyle-border-insideh:cell-none;\n" +
                            "	mso-tstyle-border-insidev:cell-none;}\n" +
                            "</style>\n" +
                            "<![endif]--><!--[if gte mso 9]><xml>\n" +
                            " <o:shapedefaults v:ext=\"edit\" spidmax=\"3074\"/>\n" +
                            "</xml><![endif]--><!--[if gte mso 9]><xml>\n" +
                            " <o:shapelayout v:ext=\"edit\">\n" +
                            "  <o:idmap v:ext=\"edit\" data=\"1\"/>\n" +
                            " </o:shapelayout></xml><![endif]-->\n" +
                            "</head>\n" );
              out.write(    "\n" +
                            "<body lang=EN-US style='tab-interval:.5in'>\n" +
                            "\n" +
                            "<div class=WordSection1>\n" +
                            "\n" +
                            "<p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "115%;font-family:\"DecoType Thuluth\"'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "115%;font-family:\"DecoType Thuluth\"'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'>بيانات العميل<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<div align=center>\n" +
                            "\n" +
                            "<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0\n" +
                            " style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;\n" +
                            " mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-table-dir:bidi;\n" +
                            " mso-border-insideh:.5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>\n" +
                            " <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:16.25pt;mso-row-margin-right:\n" +
                            "  .55pt'>\n" +
                            "  <td width=430 colspan=10 valign=top style='width:429.5pt;border-top:solid windowtext 1.0pt;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:none;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext .5pt;\n" +
                            "  background:#D9D9D9;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>معلومات شخصية :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;border-bottom:solid windowtext 1.0pt'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:1;height:15.6pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:15.6pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الاسم\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.6pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ pi.getName() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:2;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الجنسية\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>سعودي<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:3;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>رقم\n" +
                            "  البطاقة :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ pi.getGovId() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:4;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>تاريخ الإصدار\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ toHijriString(pi.getIdIssueDate()) +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:5;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>رقم حفظ\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" );
               out.write(   "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:6;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>تاريخ\n" +
                            "  الميلاد :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=146 colspan=6 valign=top style='width:146.3pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ toHijriString(pi.getBirthday()) +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=44 valign=top style='width:43.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>المدينة\n" +
                            "  <o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=138 colspan=2 valign=top style='width:138.1pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>"+ pi.getCity() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:7;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الحالة\n" +
                            "  الاجتماعية :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ statusString +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:8;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>العنوان\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=138 colspan=4 valign=top style='width:137.65pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ pi.getResedentCity() +"-"+ pi.getResedentDistrict() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=190 colspan=5 valign=top style='width:190.3pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>"+ pi.getResedentStreet()+"-"+ pi.getResedentNumber() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:9;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>أرقام\n" +
                            "  الهاتف :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ pi.getTel() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:10;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;border-bottom:solid windowtext 1.0pt'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الإقامة\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=52 valign=top style='width:52.4pt;border-top:none;border-left:none;\n" +
                            "  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ resedentTypeString +"<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=18 valign=top style='width:18.05pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=59 valign=top style='width:58.85pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=16 colspan=2 valign=top style='width:16.25pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=182 colspan=4 valign=top style='width:182.4pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:11;height:16.25pt;mso-row-margin-right:.55pt'>\n" +
                            "  <td width=430 colspan=10 valign=top style='width:429.5pt;border:none;\n" +
                            "  border-bottom:solid windowtext 1.0pt;mso-border-top-alt:solid windowtext .5pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext .5pt;\n" +
                            "  background:#D9D9D9;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal;tab-stops:40.0pt center 209.35pt'><b><span lang=AR-SA\n" +
                            "  style='font-size:12.0pt;font-family:Arial'><span style='mso-tab-count:2'>                                                         </span>معلومات\n" +
                            "  عن العمل :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;border-bottom:solid windowtext 1.0pt'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:12;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>جهة\n" +
                            "  العمل :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ ei.getEmployer() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:13;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>عنوان\n" +
                            "  العمل :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ pi.getCity() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:14;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>المسمى\n" +
                            "  الوظيفي :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ ei.getJobTitle() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:15;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الدخل\n" +
                            "  الشهري :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>"+ ei.getSalary() +"<o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n");
                 out.write( " <tr style='mso-yfti-irow:16;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=430 colspan=10 valign=top style='width:429.55pt;border:none;\n" +
                            "  border-bottom:solid windowtext 1.0pt;mso-border-top-alt:solid windowtext .5pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext .5pt;\n" +
                            "  background:#D9D9D9;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal;tab-stops:40.0pt center 209.35pt'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:Arial'>معلومات السلعة المطلوبة<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:17;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>النوع<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>مجموعة احرام خان الصابون<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:18;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الكمية<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:19;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;border-bottom:solid windowtext 1.0pt'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>قيمة\n" +
                            "  السلعة<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:20;height:14.1pt;mso-row-margin-right:.55pt'>\n" +
                            "  <td width=430 colspan=10 valign=top style='width:429.5pt;border:none;\n" +
                            "  border-bottom:solid windowtext 1.0pt;mso-border-top-alt:solid windowtext .5pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext .5pt;\n" +
                            "  background:#D9D9D9;padding:0in 5.4pt 0in 5.4pt;height:14.1pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>اقرار<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:21;height:34.4pt;mso-row-margin-right:.55pt'>\n" +
                            "  <td width=430 colspan=10 valign=top style='width:429.5pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:34.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>اقر\n" +
                            "  بان جميع المعلومات الواردة أعلاه صحيحة و لا مانع لدي من قيام المنشأة بالتحقق\n" +
                            "  من صحتها لدى أي جهة و أتحمل المسؤولية التي تترتب على عدم صحتها و عليه أوقع.<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;border-bottom:solid windowtext 1.0pt'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:22;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الاســـم\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:23;height:16.25pt;mso-row-margin-left:.5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>التاريخ\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:16.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:24;mso-yfti-lastrow:yes;height:37.55pt;mso-row-margin-left:\n" +
                            "  .5pt'>\n" +
                            "  <td style='mso-cell-special:placeholder;border:none;padding:0in 0in 0in 0in'\n" +
                            "  width=1><p class='MsoNormal'>&nbsp;</td>\n" +
                            "  <td width=102 valign=top style='width:101.6pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:37.55pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>التوقيع\n" +
                            "  :<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=328 colspan=9 valign=top style='width:327.95pt;border-top:none;\n" +
                            "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:37.55pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <![if !supportMisalignedColumns]>\n" +
                            " <tr height=0>\n" +
                            "  <td width=1 style='border:none'></td>\n" +
                            "  <td width=102 style='border:none'></td>\n" +
                            "  <td width=52 style='border:none'></td>\n" +
                            "  <td width=18 style='border:none'></td>\n" +
                            "  <td width=59 style='border:none'></td>\n" +
                            "  <td width=8 style='border:none'></td>\n" +
                            "  <td width=8 style='border:none'></td>\n" +
                            "  <td width=1 style='border:none'></td>\n" +
                            "  <td width=44 style='border:none'></td>\n" +
                            "  <td width=138 style='border:none'></td>\n" +
                            "  <td width=1 style='border:none'></td>\n" +
                            " </tr>\n" +
                            " <![endif]>\n" +
                            "</table>\n" +
                            "\n" +
                            "</div>\n" +
                            "\n" +
                            "<p class=MsoNormal style='tab-stops:46.45pt'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:\"DecoType Naskh Variants\"'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center'><u><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:\"PT Bold Heading\";\n" +
                            "mso-ascii-font-family:\"Times New Roman\";mso-hansi-font-family:\"Times New Roman\"'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center;line-height:normal'><u><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:\"PT Bold Heading\";mso-ascii-font-family:\n" +
                            "\"Times New Roman\";mso-hansi-font-family:\"Times New Roman\"'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center;line-height:normal'><u><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:\"PT Bold Heading\";mso-ascii-font-family:\n" +
                            "\"Times New Roman\";mso-hansi-font-family:\"Times New Roman\"'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><u><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:\"PT Bold Heading\";mso-ascii-font-family:\n" +
                            "\"Times New Roman\";mso-hansi-font-family:\"Times New Roman\"'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><u><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:\"PT Bold Heading\";mso-ascii-font-family:\n" +
                            "\"Times New Roman\";mso-hansi-font-family:\"Times New Roman\"'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center;line-height:normal'><b><u><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:\"PT Bold Heading\";mso-ascii-font-family:\n" +
                            "\"Times New Roman\";mso-hansi-font-family:\"Times New Roman\"'>عقد شراء<o:p></o:p></span></u></b></p>\n" +
                            "\n" +
                            "<div align=center>\n" +
                            "\n" +
                            "<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=492\n" +
                            " style='width:491.55pt;margin-left:-56.4pt;border-collapse:collapse;border:\n" +
                            " none;mso-border-alt:solid windowtext .5pt;mso-padding-alt:0in 5.4pt 0in 5.4pt;\n" +
                            " mso-table-dir:bidi;mso-border-insideh:.5pt solid windowtext;mso-border-insidev:\n" +
                            " .5pt solid windowtext'>\n" +
                            " <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:12.9pt'>\n" +
                            "  <td width=146 valign=top style='width:146.4pt;border:solid windowtext 1.0pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:12.9pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Times New Roman\"'>نوع السلعة\n" +
                            "  المباعة<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=113 valign=top style='width:113.4pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:12.9pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Times New Roman\"'>الكمية <o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=85 valign=top style='width:85.05pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:12.9pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Times New Roman\"'>السعر\n" +
                            "  الفردي <o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=147 valign=top style='width:146.7pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:12.9pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Times New Roman\"'>السعر\n" +
                            "  الإجمالي <o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes;height:17.95pt'>\n" +
                            "  <td width=146 valign=top style='width:146.4pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:17.95pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:Arial'>مجموعة احرام خان\n" +
                            "  الصابون</span></b><b><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  \"Traditional Arabic\",\"serif\";mso-ascii-font-family:Calibri;mso-hansi-font-family:\n" +
                            "  Calibri'><o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=113 valign=top style='width:113.4pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.95pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Traditional Arabic\",\"serif\";\n" +
                            "  mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=85 valign=top style='width:85.05pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.95pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Traditional Arabic\",\"serif\";\n" +
                            "  mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=147 valign=top style='width:146.7pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.95pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center;line-height:normal'><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:\"Traditional Arabic\",\"serif\";\n" +
                            "  mso-ascii-font-family:Calibri;mso-hansi-font-family:Calibri'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            "</table>\n" +
                            "\n" +
                            "</div>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'>بحمد من الله تعالى والصلاة والسلام\n" +
                            "على سيدنا محمد صلى الله عليه وآله وسلم تم الاتفاق بين كل من:<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'>العثيمين للاستثمار سجل تجاري (4030233752\n" +
                            ") طرف أول<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'>"+ pi.getName() +" ، سعودي الجنسية ، بموجب\n" +
                            "سجل مدني رقم (</span></b><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "font-family:\"Times New Roman\"'>"+ pi.getGovId() +"</span></b><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'>). طرف ثاني<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'>تمهيد:<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'>الطرف الأول يمتلك السلعة المذكورة بعالية\n" +
                            "ورغب الطرف الثاني بشرائها ووافق الطرف الأول على بيع الطرف الثاني على الشروط\n" +
                            "التالية :<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>1. التمهيد جزء من العقد.<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>2. قيمة السلعة الإجمالية (\n" +
                            ") ريال (<span style=\"mso-spacerun:yes\">  </span>ريال ) فقط .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>3. قيمة العقد <span\n" +
                            "style=\"mso-spacerun:yes\"> </span>( ) ريال ( ريال ) كامل المبلغ .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>4. يبدأ العقد من تاريخ : 03/01/1435هـ<span\n" +
                            "style=\"mso-spacerun:yes\">  </span>وينتهي بتاريخ 02/02 / 1435 هــ .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>5. أقر وألتزم أنا الطرف\n" +
                            "الثاني المشتري بدفع مبلغ وقدره ( <span style=\"mso-spacerun:yes\"> </span>ريال ) أتعاب\n" +
                            "لتقديم الشكوى ضدي و ألف وخمسمائة ريال ( 1500) ريال عن كل جلسة أتهرب عنها ،\n" +
                            "ومبلغ (150) ريال حين ذهاب المحصل لمنزلي او مقر عملي وذلك بعد مرور مدة اقصاها (\n" +
                            "خمسة أيام ) من تاريخ استحقاق المبلغ .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>6. ألتزم أنا الطرف الثاني\n" +
                            "بدفع<span style=\"mso-spacerun:yes\">  </span>( 750 ) ريال ، سبعمائة وخمسون ريال ،\n" +
                            "مصاريف إدارية غير مستحقة الاسترداد .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>7. أقر المشتري بأنه عاين\n" +
                            "السلعة المذكورة بعالية معاينة نافية للجهالة شرعاً وقبلها على حالها وأستلمها.<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>8. المحكمة الشرعية في\n" +
                            "منطقة البيع هي الفاصل في حالة المنازعة.<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>9. أقر أنا الطرف الثاني\n" +
                            "المشتري بأنه لا يحق لي رفع أي دعوة ضد الطرف الأول بخصوص هذا العقد .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>10. أقر أنا الطرف الثاني\n" +
                            "المشتري بأني أزكي الشهود وأوافق على شهادتهم على هذا العقد وهم :<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'><span\n" +
                            "style=\"mso-spacerun:yes\">      </span>رائد غازي غيث .<span\n" +
                            "style=\"mso-spacerun:yes\">                     </span>عبد الله <span\n" +
                            "style=\"mso-spacerun:yes\"> </span>جود الله القرني .<span\n" +
                            "style=\"mso-spacerun:yes\">                       </span></span></b><b><span\n" +
                            "style='font-size:12.0pt;font-family:Arial'><o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>12. أقر الطرف الثاني\n" +
                            "المشتري بموافقته على جميع ما ذكر دون غبن أو إكراه.<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'>13. حرر بتاريخ : 06/11/2013م\n" +
                            "، بمدينة جدة بين : الطرف الاول : العثيمين للاستثمار سجل تجاري ( 4030233752 )<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='margin-left:4.15pt;text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'><span\n" +
                            "style=\"mso-spacerun:yes\">                               </span><span\n" +
                            "style=\"mso-spacerun:yes\">                      </span><span\n" +
                            "style=\"mso-spacerun:yes\"> </span>الطرف الثاني : .<o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='margin-left:4.15pt;text-align:right;line-height:normal'><b><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;font-family:Arial'><span\n" +
                            "style=\"mso-spacerun:yes\">         </span><span\n" +
                            "style=\"mso-spacerun:yes\">                 </span><span\n" +
                            "style=\"mso-spacerun:yes\">                            </span><span\n" +
                            "style=\"mso-spacerun:yes\">   </span><span style=\"mso-spacerun:yes\"> </span>هوية\n" +
                            "رقم : ("+ pi.getGovId() +" ) .<span style=\"mso-spacerun:yes\">   </span><span\n" +
                            "style=\"mso-spacerun:yes\"> </span></span></b><b><span style='font-size:12.0pt;\n" +
                            "font-family:Arial'><o:p></o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'><span\n" +
                            "style=\"mso-spacerun:yes\">                                                                                  \n" +
                            "</span><span style=\"mso-spacerun:yes\">  </span><span\n" +
                            "style=\"mso-spacerun:yes\">    </span><span style=\"mso-spacerun:yes\"> </span><o:p></o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center'><b><u><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:\"Traditional Arabic\",\"serif\";\n" +
                            "mso-ascii-font-family:Arial;mso-hansi-font-family:Arial'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center'><b><u><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>أقرار\n" +
                            "استلام بضاعة و تفويض بيع<o:p></o:p></span></u></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal align=center style='text-align:center'><i><u><span\n" +
                            "lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p><span\n" +
                            " style='text-decoration:none'>&nbsp;</span></o:p></span></u></i></p>\n" +
                            "\n" +
                            "<div align=center>\n" +
                            "\n" +
                            "<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0\n" +
                            " style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;\n" +
                            " mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-table-dir:bidi;\n" +
                            " mso-border-insideh:.5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>\n" +
                            " <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\n" +
                            "  <td width=80 valign=top style='width:79.65pt;border:solid windowtext 1.0pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>الاســـــــــم : <o:p></o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=346 valign=top style='width:346.45pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>"+ pi.getName() +"<o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:1'>\n" +
                            "  <td width=80 valign=top style='width:79.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>رقم الهـوية : <o:p></o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=346 valign=top style='width:346.45pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>"+ pi.getGovId() +"<o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:2;height:19.85pt'>\n" +
                            "  <td width=80 valign=top style='width:79.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:19.85pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>العنــــــوان :<o:p></o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=346 valign=top style='width:346.45pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:19.85pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>"+ pi.getResedentCity()+"-"+ pi.getResedentDistrict() +"-"+ pi.getResedentStreet() +"<o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:3;mso-yfti-lastrow:yes'>\n" +
                            "  <td width=80 valign=top style='width:79.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>جهة العمل :<o:p></o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=346 valign=top style='width:346.45pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  left;line-height:normal'><span lang=AR-SA style='font-size:12.0pt;font-family:\n" +
                            "  Arial'>"+ ei.getEmployer() +"<o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            "</table>\n" +
                            "\n" +
                            "</div>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'>اقر أنا الموضح اسمي بعالية باني\n" +
                            "استلمت البضاعة وهي عبارة عن <b>مجموعة احرام خان الصابون</b> بقيمة ( <span\n" +
                            "style=\"mso-spacerun:yes\"> </span>) ريال من <b>العثيمين للاستثمار </b>وأفوضهم\n" +
                            "ببيعها لمن يرغب في شرائها حسب السوق و ايداع المبالغ بالحسابات المذكورة .<o:p></o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "\n" +
                            "<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0\n" +
                            " style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;\n" +
                            " mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-table-dir:bidi;\n" +
                            " mso-border-insideh:.5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>\n" +
                            " <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:17.7pt'>\n" +
                            "  <td width=40 valign=top style='width:39.65pt;border:solid windowtext 1.0pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.7pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>م<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=135 valign=top style='width:134.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.7pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>البنك<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=226 valign=top style='width:226.25pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.7pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>رقم\n" +
                            "  الحساب<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border:solid windowtext 1.0pt;\n" +
                            "  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:\n" +
                            "  solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:17.7pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>المبلغ<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:1;height:19.5pt'>\n" +
                            "  <td width=40 valign=top style='width:39.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:19.5pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>1<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=135 valign=top style='width:134.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:19.5pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=226 valign=top style='width:226.25pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:19.5pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:19.5pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:2'>\n" +
                            "  <td width=40 valign=top style='width:39.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>2<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=135 valign=top style='width:134.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=226 valign=top style='width:226.25pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:3'>\n" +
                            "  <td width=40 valign=top style='width:39.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>3<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=135 valign=top style='width:134.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=226 valign=top style='width:226.25pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:4'>\n" +
                            "  <td width=40 valign=top style='width:39.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>4<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=135 valign=top style='width:134.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=226 valign=top style='width:226.25pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:5'>\n" +
                            "  <td width=40 valign=top style='width:39.65pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>5<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=135 valign=top style='width:134.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=226 valign=top style='width:226.25pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:6;mso-yfti-lastrow:yes'>\n" +
                            "  <td width=401 colspan=3 valign=top style='width:400.55pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal align=center style='text-align:center'><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;line-height:115%;font-family:Arial'>الاجـــمــــالـــــــــــــي<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=134 valign=top style='width:133.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;\n" +
                            "  mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            "</table>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'>التاريخ : 03/01/1435هـ<o:p></o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'>المقر بما فيه : <b><span\n" +
                            "style=\"mso-spacerun:yes\"> </span></b><span style=\"mso-spacerun:yes\"> </span>.<o:p></o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'>التوقيع<span\n" +
                            "style=\"mso-spacerun:yes\">       </span>:<o:p></o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'>البصمة<span\n" +
                            "style=\"mso-spacerun:yes\">      </span>:<o:p></o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:right'><span lang=AR-SA style='font-size:\n" +
                            "12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:justify'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:justify'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:justify'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:justify'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='text-align:justify'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;line-height:115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal style='line-height:normal'><b><span lang=AR-SA\n" +
                            "style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0\n" +
                            " style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext 2.25pt;\n" +
                            " mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-table-dir:bidi;\n" +
                            " mso-border-insideh:2.25pt solid windowtext;mso-border-insidev:2.25pt solid windowtext'>\n" +
                            " <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>\n" +
                            "  <td width=493 colspan=3 valign=top style='width:492.7pt;border:solid windowtext 2.25pt;\n" +
                            "  background:#999999;mso-shading:windowtext;mso-pattern:gray-40 auto;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>سنـــــــــد لأمــــــــــــــــر( 0845/12/13)<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:1'>\n" +
                            "  <td width=493 colspan=3 valign=top style='width:492.7pt;border:none;\n" +
                            "  mso-border-top-alt:solid windowtext 2.25pt;padding:0in 5.4pt 0in 5.4pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'>أتعهد بأن أدفـع هذا السند لأمر العثيمين للاستثمار ( تقسيط\n" +
                            "  ) ، مبلــــــغ وقــدره رقمــاً: ( ) ريــال كتابة <span\n" +
                            "  style=\"mso-spacerun:yes\">                                               </span>(\n" +
                            "  ) ريال في تاريخ : 02/02/1435هـ<o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:2;height:31.15pt'>\n" +
                            "  <td width=225 valign=top style='width:225.05pt;border:none;padding:0in 5.4pt 0in 5.4pt;\n" +
                            "  height:31.15pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>مكان\n" +
                            "  التحرير/ جــــــــــدة <o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=268 colspan=2 valign=bottom style='width:267.65pt;border:none;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:31.15pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'><span style=\"mso-spacerun:yes\">               </span>الموافـــــــــــق\n" +
                            "  : <span style=\"mso-spacerun:yes\"> </span>"+ todayHijri.toString() +"<o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:3;height:35.25pt'>\n" +
                            "  <td width=225 valign=top style='width:225.05pt;border:none;padding:0in 5.4pt 0in 5.4pt;\n" +
                            "  height:35.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>الاسـم–\n" +
                            "  <span style=\"mso-spacerun:yes\"> </span></span></b><b><span lang=AR-SA\n" +
                            "  style='font-size:12.0pt;font-family:\"Times New Roman\"'><span\n" +
                            "  style=\"mso-spacerun:yes\"> </span>"+ pi.getName() +"<span style=\"mso-spacerun:yes\">  </span></span></b><b><span\n" +
                            "  lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=268 colspan=2 valign=bottom style='width:267.65pt;border:none;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:35.25pt'>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'>العنوان –<span style=\"mso-spacerun:yes\">  </span><span\n" +
                            "  style=\"mso-spacerun:yes\"> </span>"+ pi.getResedentCity()+"-"+ pi.getResedentDistrict()+"-"+ pi.getResedentStreet() +"<o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:4;height:27.25pt'>\n" +
                            "  <td width=225 valign=top style='width:225.05pt;border:none;padding:0in 5.4pt 0in 5.4pt;\n" +
                            "  height:27.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'>توقيع\n" +
                            "  المتعهد –<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=268 colspan=2 valign=bottom style='width:267.65pt;border:none;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:27.25pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><span\n" +
                            "  style=\"mso-spacerun:yes\">               </span>بصمة المتعهد –<o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal align=center style='margin-bottom:0in;margin-bottom:.0001pt;\n" +
                            "  text-align:center;line-height:normal'><b><span lang=AR-SA style='font-size:\n" +
                            "  12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n" +
                            "  normal'><b><span lang=AR-SA style='font-size:12.0pt;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:5;height:24.65pt'>\n" +
                            "  <td width=493 colspan=3 valign=top style='width:492.7pt;border:none;\n" +
                            "  border-bottom:solid windowtext 2.25pt;padding:0in 5.4pt 0in 5.4pt;height:\n" +
                            "  24.65pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'>هذا السند لأمر واجب الدفع دون الرجوع بلا مصروفات وبدون\n" +
                            "  احتجاج ( البروتستو)<o:p></o:p></span></b></p>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <tr style='mso-yfti-irow:6;mso-yfti-lastrow:yes;height:54.2pt'>\n" +
                            "  <td width=232 colspan=2 valign=top style='width:232.15pt;border:solid windowtext 2.25pt;\n" +
                            "  border-top:none;mso-border-top-alt:solid windowtext 2.25pt;background:#B2B2B2;\n" +
                            "  mso-shading:windowtext;mso-pattern:gray-30 auto;padding:0in 5.4pt 0in 5.4pt;\n" +
                            "  height:54.2pt'>\n" +
                            "  <p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;text-align:\n" +
                            "  justify;line-height:normal'><b><span lang=AR-SA style='font-size:12.0pt;\n" +
                            "  font-family:Arial'>هذا السند واجب الدفع بدون تعطل بموجب قرار مجلس الوزراء\n" +
                            "  الموقر رقم 692 وتاريخ 26/9/1383 هـ<span style=\"mso-spacerun:yes\">  \n" +
                            "  </span>والمتوج بالمرسوم الملكي الكريم رقم 37 وتاريخ 11/10/1383 هـ<span\n" +
                            "  style=\"mso-spacerun:yes\">  </span>نظام الأوراق التجارية<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            "  <td width=261 valign=top style='width:260.55pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 2.25pt;border-right:solid windowtext 2.25pt;\n" +
                            "  mso-border-top-alt:solid windowtext 2.25pt;mso-border-left-alt:solid windowtext 2.25pt;\n" +
                            "  background:#B2B2B2;mso-shading:windowtext;mso-pattern:gray-30 auto;\n" +
                            "  padding:0in 5.4pt 0in 5.4pt;height:54.2pt'>\n" +
                            "  <p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "  115%;font-family:Arial'>بموجب هذا السند يسقط المدين و الكفيل الغارم كافة حقوق\n" +
                            "  التقدم و المطالبة و الاحتجاج و الإخطار بالامتناع عن الوفاء بهذا السند كما يحق\n" +
                            "  للدائن بموجب هذا السند الرجوع على المدين أو الكفيل الغارم منفردين أو مجتمعين<o:p></o:p></span></b></p>\n" +
                            "  </td>\n" +
                            " </tr>\n" +
                            " <![if !supportMisalignedColumns]>\n" +
                            " <tr height=0>\n" +
                            "  <td width=225 style='border:none'></td>\n" +
                            "  <td width=7 style='border:none'></td>\n" +
                            "  <td width=261 style='border:none'></td>\n" +
                            " </tr>\n" +
                            " <![endif]>\n" +
                            "</table>\n" +
                            "\n" +
                            "<p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "<p class=MsoNormal><b><span lang=AR-SA style='font-size:12.0pt;line-height:\n" +
                            "115%;font-family:Arial'><o:p>&nbsp;</o:p></span></b></p>\n" +
                            "\n" +
                            "</div>\n" +
                            "\n" +
                            "</body>\n" +
                            "\n" +
                            "</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
