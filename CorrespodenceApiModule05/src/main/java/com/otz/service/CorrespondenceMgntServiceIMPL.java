package com.otz.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.otz.bindings.CoSummary;
import com.otz.entity.CitizenAppRegistrationEntity;
import com.otz.entity.CoTriggersEntity;
import com.otz.entity.DcCaseEntity;
import com.otz.entity.ElgibilityDetailsEntity;
import com.otz.repository.ICitizenRepository;
import com.otz.repository.ICoTriggerRepository;
import com.otz.repository.IDcCaseRepository;
import com.otz.repository.IElgibilityDetermineRepository;
import com.otz.util.EmailUtils;
@Service
public class CorrespondenceMgntServiceIMPL implements ICorrespondenceMgntService {
	@Autowired
	private ICoTriggerRepository triggerRepo;
	@Autowired
	private IElgibilityDetermineRepository elgiRepo;
	@Autowired
	private IDcCaseRepository caseRepo;
	@Autowired
	private ICitizenRepository citizenRepo;
	@Autowired
	private EmailUtils emailUtils;
	int successTriggers=0;
	int pendingTriggers=0;
	@Override
	public CoSummary processPendingTriggers()  {
		CitizenAppRegistrationEntity citizenEntity=null;
		ElgibilityDetailsEntity elgiEntity=null;
		
		
		//get all pending triggers
		List<CoTriggersEntity> triggersList=triggerRepo.findByTriggerStatus("Pending");
		//process each pending triggers
		CoSummary coSummary=new CoSummary();  
		coSummary.setTotalTriggers(triggersList.size());
		
		//Process the trigger in multithreaded evirnoment
		ExecutorService executorService=Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Object> pool=new ExecutorCompletionService<Object>(executorService);
		
		for (CoTriggersEntity entity : triggersList) {
			pool.submit(new Callable<Object>(){
				@Override
				public Object call() throws Exception {
					try {
						processTrigger(coSummary, entity); 
						successTriggers++;
					} catch (Exception e) {
						e.printStackTrace();
						pendingTriggers++;
					}
					return null;
				}
			});
			
		}// end of for each
		
		
		
		coSummary.setPendingTriggers(pendingTriggers);
		coSummary.setSuccessTriggers(successTriggers);
		return coSummary;
	}
	//helper method
	private CitizenAppRegistrationEntity processTrigger(CoSummary summary,CoTriggersEntity entity) throws Exception {
		CitizenAppRegistrationEntity citizenEntity=null;
		//get elgiblity details based on case no
		ElgibilityDetailsEntity elgiEntity = elgiRepo.findByCaseNo(entity.getCaseNo());
		//get appid  based on case no
		Optional<DcCaseEntity> optCaseEntity=caseRepo.findById(entity.getCaseNo());
		if (optCaseEntity.isPresent()) {
			DcCaseEntity caseEntity=optCaseEntity.get();
			Integer appid=caseEntity.getAppId();
			//get citizen details based on appId 
			Optional<CitizenAppRegistrationEntity> optCitizenEntity=citizenRepo.findById(appid);
			if (optCitizenEntity.isPresent()) {
				citizenEntity=optCitizenEntity.get();
			}//inner if
		}//if outer
		//generate pdf doc having elgibility details and send the pdf doc as email
		
			generatePDFAndSendMail(elgiEntity, citizenEntity);
			return citizenEntity;
			
	}
	//helper method
	private void generatePDFAndSendMail(ElgibilityDetailsEntity elgiEntity,CitizenAppRegistrationEntity citizenEntity) throws Exception{
        //create Document obj (OpenPdf)
		Document document=new Document(PageSize.A4);
		//create pdf file to write the content to it
		File file=new File(elgiEntity.getCaseNo()+".pdf");
		FileOutputStream fos=new FileOutputStream(file);
		//get PDFWriter to wirte the document and response object
		PdfWriter.getInstance(document, fos);
		//open the document
		document.open();
		//Define Font for the paragraph
		Font font=FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);
		font.setColor(Color.red);
		//create Paragraph Having content and above font Style
		Paragraph para=new Paragraph("Plan Approval/Denial Communication",font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		//add Paragraph to document
		document.add(para);
		
		//Display Search result as the Pdf table
		PdfPTable table=new PdfPTable(10);
		table.setWidthPercentage(90);
		table.setWidths(new float[] {3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f});
		table.setSpacingBefore(1.0f);

		//prepare heading row in the pdf table
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(2);
		Font cellFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		cellFont.setColor(Color.BLACK);
		cell.setPhrase(new Phrase("Trace Id",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Case No",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Holder Name",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Holder SSN",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Plan Name",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Plan Status",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("StartDate",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("EndDate",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Benifit Amount",cellFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Denial Reason",cellFont));
		table.addCell(cell);
		
		//add data cells to pdftable
			table.addCell(String.valueOf(elgiEntity.getEdTraceId()));
			table.addCell(String.valueOf(elgiEntity.getCaseNo()));
			table.addCell(elgiEntity.getHolderName());
			table.addCell(String.valueOf(elgiEntity.getHolderSSN()));
			table.addCell(elgiEntity.getPlanName());
			table.addCell(elgiEntity.getPlanStatus());
			table.addCell(String.valueOf(elgiEntity.getPlanStartDate()));
			table.addCell(String.valueOf(elgiEntity.getPlanEndDate()));
			table.addCell(String.valueOf(elgiEntity.getBenifitAmount()));
			table.addCell(elgiEntity.getDenialReason());
		//add table to document
		document.add(table);
		document.close();
		
		//send the generated pdf doc
		String subject="Plan Approval/Denial Mail";
		String body="Hello Mr/Miss/Mrs."+citizenEntity.getFullName()+" This mail Conatins Complete Details plan Approval or Denial ";
		
		emailUtils.sendEmailMessage(citizenEntity.getEmail(),subject,body,file);
		updateCoTrigger(elgiEntity.getCaseNo(),file);
	}

	private void updateCoTrigger(Integer caseNo, File file) throws Exception {
		//check Trigger avilable based on caseNo
		CoTriggersEntity coTriggersEntity=triggerRepo.findByCaseNo(caseNo);
		//get byte[] representing pdf con content
		byte[] pdfContent=new byte[(int) file.length()];
		FileInputStream fis=new FileInputStream(file);
		fis.read(pdfContent);
		if (coTriggersEntity!=null) {
			coTriggersEntity.setCoNoticePdf(pdfContent);
			coTriggersEntity.setTriggerStatus("Completed");
			triggerRepo.save(coTriggersEntity);
		}
		fis.close();
	}
}