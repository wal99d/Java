

function showLoading(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
            } else if(data.status == "success") {
                document.getElementById('loadingmsg').style.visibility = 'hidden';
                document.getElementById('loadingmsg').style.opacity = 0;
                document.getElementById('loadingover').style.visibility = 'hidden';
                 document.getElementById('loadingover').style.opacity = 0;
                 //$('#create:messages').fadeIn(1000);
                
            }
}

function showAjaxLoading(){
    document.getElementById('loadingmsg').style.visibility = 'visible';
    document.getElementById('loadingmsg').style.opacity = 1;
    document.getElementById('loadingover').style.visibility = 'visible';
    document.getElementById('loadingover').style.opacity = 1;
}

function hideAjaxLoading(){
    document.getElementById('loadingmsg').style.visibility = 'hidden';
    document.getElementById('loadingmsg').style.opacity = 0;
    document.getElementById('loadingover').style.visibility = 'hidden';
     document.getElementById('loadingover').style.opacity = 0;
}

function showLoadingTables(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
            } else if(data.status == "success") {
                document.getElementById('loadingmsg').style.visibility = 'hidden';
                document.getElementById('loadingmsg').style.opacity = 0;
                document.getElementById('loadingover').style.visibility = 'hidden';
                 document.getElementById('loadingover').style.opacity = 0;
                
                
            }
}

function showLoadingFile(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('fileUploadCompleted').style.visibility = 'visible';
                document.getElementById('fileUploadCompleted').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
                
                $("#fileUploadCompleted").fadeOut(20);
                $("#loadingover").fadeOut(20);
            }
}


// for Wared Messages
function closeMeWared(){
    $("#completedWared").fadeOut(20);
    $("#loadingoverWaredMessages").fadeOut(20);
}

function showLoadingWaredScanned(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('completedWaredScanned').style.visibility = 'visible';
                document.getElementById('completedWaredScanned').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
}

function closeMeWaredScanned(){
    $("#completedWaredScanned").fadeOut(20);
    //$("#loadingWaredover").fadeOut(500);
}

// for Sader Messages
function closeMeSader(){
    $("#completedSader").fadeOut(20);
    $("#loadingoverSaderMessages").fadeOut(20);
    
}

function showLoadingScanned(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('completedScanned').style.visibility = 'visible';
                document.getElementById('completedScanned').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
}

function closeMeScanned(){
    $("#completedScanned").fadeOut(20);
    //$("#loadingWaredover").fadeOut(500);
}

function printMe(id){
     var contentToPrint = document.getElementById(id);
      var targetContent = window.open();
     targetContent.document.write("<html dir=\"rtl\"><head><link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"/DArch/faces/resources/CSS/print.css\" /></head><body><div id=\"content\">"+ contentToPrint.outerHTML +"</div></body></html>");
     targetContent.print();
}


/**
* trimTail function trims the ending , after row reading ends
*/
function trimTail(str){
    var tail = str.substring(0,str.length-1);  return tail;
}
/**
* main function that is to be called to read table content before saving
*/
function exportToCSV(t){
    var table = document.getElementById(t);
    var rowLength = table.rows.length;
    var colLength = table.rows[0].cells.length;
    var header = "";
    var body = "";                
    for(var i=0;i<colLength;i++){
        header = header+table.rows[0].cells[i].innerHTML+",";
    }
    header = trimTail(header);                
    for(var j=1;j<rowLength;j++){
        for(var k=0;k<colLength;k++){// reading content of each column
            body = body+table.rows[j].cells[k].innerHTML+",";
        }                    
        body = trimTail(body)+'\r\n';
    }                
    body = header+ '\r\n'+body;
    saveFile(body);
}
/**
*saving option as per browser
*/
  function saveFile(str){
     if (navigator.appName != 'Microsoft Internet Explorer'){
        window.open('data:text/csv;charset=utf-8,' + escape(str));
    }  else{
        var popup = window.open('data:text/csv;charset=utf-8,' + escape(str));
        popup.document.body.innerHTML = '<pre>' + str + '</pre>';
    }
}

function openStatusFileInfo(data) {
    if(data.status == "success") {
        window.open('/LOAN_SYSTEM/faces/printStatusFile.xhtml',"","width=400, height=500");
    }
}
function openIncomingArchiveFileInfo(data) {
    if(data.status == "success") {
        window.open('/DArch/faces/printIncomingArchiveFile.xhtml',"","width=400, height=500");
    }
}


function showScannedWaredFile(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('scannedWaredFile').style.visibility = 'visible';
        document.getElementById('scannedWaredFile').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#scannedWaredFile").fadeIn(20);
    }
    
}

function closeScannedWaredFileWindow(){
    $("#scannedWaredFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

function showScannedFile(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingover').style.visibility = 'visible';
                document.getElementById('loadingover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingover').style.visibility = 'visible';
        document.getElementById('loadingover').style.opacity = 1;
        document.getElementById('scannedFile').style.visibility = 'visible';
        document.getElementById('scannedFile').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#scannedFile").fadeIn(20);
    }
}

function closeScannedFileWindow(){
    $("#scannedFile").fadeOut(20);
    $("#loadingover").fadeOut(20);
}

//ALL Info 
function showAllInfo(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('allInfoModel').style.visibility = 'visible';
        document.getElementById('allInfoModel').style.opacity = 1;
        $("#allInfoModel").fadeIn(20);
        
        
    }
}

function closeAllInfoModel(){
    $("#allInfoModel").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//All Info Archived
function showAllInfoArchived(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('allInfoModelArchived').style.visibility = 'visible';
        document.getElementById('allInfoModelArchived').style.opacity = 1;
        $("#allInfoModelArchived").fadeIn(20);
        
        
    }
}

function closeAllInfoArchivedModel(){
    $("#allInfoModelArchived").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

function showProfit(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('profitMarginModal').style.visibility = 'visible';
        document.getElementById('profitMarginModal').style.opacity = 1;
        $("#profitMarginModal").fadeIn(20);
        
        
    }
}

function closeProfitModel(){
    $("#profitMarginModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}
//Contracts Scanned
function showScannedContract(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('scannedContract').style.visibility = 'visible';
        document.getElementById('scannedContract').style.opacity = 1;
       // $("#loadingWaredover").fadeIn(20);
        $("#scannedContract").fadeIn(20);
    }
    
}

function closeScannedContracts(){
    $("#scannedContract").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}
function showLoadingScannedContracts(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('completedScanned').style.visibility = 'visible';
                document.getElementById('completedScanned').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
}

function closeMeScannedContract(){
    $("#completedScanned").fadeOut(20);
    //$("#loadingWaredover").fadeOut(500);
}


//Scanned Paid Paper 
function showScannedPaidPaper(data){
     if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('scannedPaidPaper').style.visibility = 'visible';
        document.getElementById('scannedPaidPaper').style.opacity = 1;
       // $("#loadingWaredover").fadeIn(20);
        $("#scannedPaidPaper").fadeIn(20);
    }
    
}
function showLoadingScannedPaidPaper(data) {
            if(data.status == "success") {
                //$("#loadingmsg").fadeOut(20);
                document.getElementById('completedPaidPaperScanned').style.visibility = 'visible';
                document.getElementById('completedPaidPaperScanned').style.opacity = 1;
                //document.getElementById('loadingWaredover').style.visibility = 'visible';
                //document.getElementById('loadingWaredover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
}
function closeMeScannedPaidPaperContract(){
     $("#completedPaidPaperScanned").fadeOut(20);
}

function closeScannedPaidPaper(){
    $("#scannedPaidPaper").fadeOut(20);
    
}

//Scanned Letter Paper 
function showScannedLetter(data){
     if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('scannedLetter').style.visibility = 'visible';
        document.getElementById('scannedLetter').style.opacity = 1;
       // $("#loadingWaredover").fadeIn(20);
        $("#scannedLetter").fadeIn(20);
    }
    
}
function showLoadingScannedLetter(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('completedLetterScanned').style.visibility = 'visible';
                document.getElementById('completedLetterScanned').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
            
}
function closeMeScannedLetter(){
     $("#completedLetterScanned").fadeOut(20);
}

function closeLetterPaper(){
    $("#scannedLetter").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Scanned Receipte Paper 
function showScannedReceiptePaper(data){
     if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('scannedReceiptePaidPaper').style.visibility = 'visible';
        document.getElementById('scannedReceiptePaidPaper').style.opacity = 1;
       // $("#loadingWaredover").fadeIn(20);
        $("#scannedReceiptePaidPaper").fadeIn(20);
    }
    
}
function showLoadingScannedReciptePaper(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('completedReceiptePaperScanned').style.visibility = 'visible';
                document.getElementById('completedReceiptePaperScanned').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
            
}
function closeMeScannedReceiptePaper(){
     $("#completedReceiptePaperScanned").fadeOut(20);
}

function closeScannedRecieptePaper(){
    $("#scannedReceiptePaidPaper").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Request Downloaded File
function showDownloadedFile(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('requestDownloadedFile').style.visibility = 'visible';
        document.getElementById('requestDownloadedFile').style.opacity = 1;
        $("#requestDownloadedFile").fadeIn(20);
        document.getElementById("refreshFileBtn").click();
        
    }
}

function closeDownloadedFileView(){
    $("#requestDownloadedFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Approved Doc File
function showApprovedDocFile(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('approvedDcoModal').style.visibility = 'visible';
        document.getElementById('approvedDcoModal').style.opacity = 1;
        $("#approvedDcoModal").fadeIn(20);
        document.getElementById("refreshApprovedDocFileBtn").click();
        
    }
}

function closeApprovedFileView(){
    $("#approvedDcoModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Paid Downloaded File
function showDownloadedPaidFile(data) {
     if (data.status == "success") {
        //$("#loadingmsg").fadeOut(20);
        document.getElementById('paidDownloadedFile').style.visibility = 'visible';
        document.getElementById('paidDownloadedFile').style.opacity = 1;
        $("#paidDownloadedFile").fadeIn(20);
        document.getElementById("refreshPaidFileBtn").click();
        
    }
}

function closeDownloadedPaidFileView(){
    $("#paidDownloadedFile").fadeOut(20);
    
}

//Paid Archived Downloaded File
function showDownloadedArchivedPaidFile(data) {
     if (data.status == "success") {
        //$("#loadingmsg").fadeOut(20);
        document.getElementById('paidArchivedDownloadedFile').style.visibility = 'visible';
        document.getElementById('paidArchivedDownloadedFile').style.opacity = 1;
        $("#paidArchivedDownloadedFile").fadeIn(20);
        document.getElementById("refreshPaidArchivedFileBtn").click();
        
    }
}

function closeDownloadedArchivedPaidFileView(){
    $("#paidArchivedDownloadedFile").fadeOut(20);
    
}

//Paid Paper Downloaded File
function showDownloadedPaidPaperFile(data) {
     if (data.status == "success") {
        //$("#loadingmsg").fadeOut(20);
        document.getElementById('paidPaperDownloadedFile').style.visibility = 'visible';
        document.getElementById('paidPaperDownloadedFile').style.opacity = 1;
        $("#paidPaperDownloadedFile").fadeIn(20);
        document.getElementById("refreshPaidPaperFileBtn").click();
        
    }
}

function closeDownloadedPaidPaperFileView(){
    $("#paidPaperDownloadedFile").fadeOut(20);
    
}

//Final Letter Downloaded File
function showDownloadedFinalLetterFile(data) {
     if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('finalLetterDownloadedFile').style.visibility = 'visible';
        document.getElementById('finalLetterDownloadedFile').style.opacity = 1;
        $("#finalLetterDownloadedFile").fadeIn(20);
        document.getElementById("refreshArchivedFinalLetterFileBtn").click();
        
    }
}

function closeDownloadedFinalLetterFileView(){
    $("#finalLetterDownloadedFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Request Archived Downloaded File
function showArchivedDownloadedFile(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('requestArchivedDownloadedFile').style.visibility = 'visible';
        document.getElementById('requestArchivedDownloadedFile').style.opacity = 1;
        $("#requestArchivedDownloadedFile").fadeIn(20);
        document.getElementById("refreshArchivedFileBtn").click();
        
    }
}
function closeArchivedDownloadedFileView(){
    $("#requestArchivedDownloadedFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Contract file generation
function generateContract(data){
    if (data.status == "success") {
        document.getElementById("refresh_contract_file").click();
    }
}

//Final letter file generation
function generateLetter(data){
    if (data.status == "success") {
        document.getElementById("refresh_letter_file").click();
    }
}

//Wared Letter Downloaded File
function showWaredLetterDownloadedFile(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('waredDownloadedLetterFile').style.visibility = 'visible';
        document.getElementById('waredDownloadedLetterFile').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#waredDownloadedLetterFile").fadeIn(20);
        document.getElementById("refreshWaredLetterFileBtn").click();
        
    }
}

//Sader Downloaded File
function showSaderDownloadedFile(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('saderDownloadedFile').style.visibility = 'visible';
        document.getElementById('saderDownloadedFile').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#saderDownloadedFile").fadeIn(20);
        document.getElementById("refreshSaderFileBtn").click();
        
    }
}

function closeSaderDownloadedFileView(){
    $("#saderDownloadedFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Sader Downloaded Letter File
function showSaderDownloadedLetterFile(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('saderDownloadedLetterFile').style.visibility = 'visible';
        document.getElementById('saderDownloadedLetterFile').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#saderDownloadedLetterFile").fadeIn(20);
        document.getElementById("refreshSaderLetterFileBtn").click();
        
    }
}

function closeSaderDownloadedLetterFileView(){
    $("#saderDownloadedLetterFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

function closeWaredLetterDownloadedFileView(){
    $("#waredDownloadedLetterFile").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//Diverts Sader
function showScannedSaderFileDiverts(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('scannedSaderFileDiverts').style.visibility = 'visible';
        document.getElementById('scannedSaderFileDiverts').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#scannedSaderFileDiverts").fadeIn(20);
    }
    
}

function closeScannedSaderFileWindowDiverts(){
    $("#scannedSaderFileDiverts").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}
function showLoadingSaderScannedDiverts(data) {
           if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
            } else if(data.status == "success") {
                $("#loadingmsg").fadeOut(20);
                document.getElementById('completedSaderScannedDiverts').style.visibility = 'visible';
                document.getElementById('completedSaderScannedDiverts').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                //$("#completed").fadeOut(200);
                //$("#loadingover").fadeOut(200);
            }
}

function closeMeSaderScannedDiverts(){
    $("#completedSaderScannedDiverts").fadeOut(20);
    //$("#loadingWaredover").fadeOut(500);
}
///////////////////

function openOutgoingFileInfo(data) {
    if(data.status == "success") {
        window.open('/DArch/faces/printOutgoingFile.xhtml',"","width=400, height=500");
    }
}
function openOutgoingArchiveFileInfo(data) {
    if(data.status == "success") {
        window.open('/DArch/faces/printOutgoingArchiveFile.xhtml',"","width=400, height=500");
    }
}


function dimissModal(){
    $("#toDosModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    document.getElementById("toDosForm:refreshMe").click();
}
function closeToDos(){
    $("#toDosModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}
function doProcess(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('toDosModal').style.visibility = 'visible';
        document.getElementById('toDosModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#toDosModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}
function dimissModalOut(){
    $("#saderModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    document.getElementById("divertForm:refreshMe2").click();
}
function closeSader(){
    $("#saderModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//do Process Payment
function doProcessPayment(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('paymentModal').style.visibility = 'visible';
        document.getElementById('paymentModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#paymentModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}
function closePayment(){
    $("#paymentModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

function doProcessOut(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('saderModal').style.visibility = 'visible';
        document.getElementById('saderModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#saderModal").fadeIn(20);
    }
}


function doProcessSaderSearch(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('saderSearcModal').style.visibility = 'visible';
        document.getElementById('saderSearcModal').style.opacity = 1;
       // $("#loadingWaredover").fadeIn(20);
        $("#saderSearcModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalOutSaderSearch() {
    $("#saderSearcModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeSaderSearch(){
    $("#saderSearcModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}
// FOR ARCHIVED SEARCH
function doProcessArchivedSearch(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('archivedNotesModal').style.visibility = 'visible';
        document.getElementById('archivedNotesModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#archivedNotesModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalOutWaredSearch() {
    $("#archivedNotesModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeArchivedNotes(){
    $("#archivedNotesModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//FOR SMS 
function doProcessSMS(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('smsModal').style.visibility = 'visible';
        document.getElementById('smsModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#smsModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalSMS(data) {
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
             $("#loadingmsg").fadeOut(10);
             $("#loadingWaredover").fadeOut(10);
             $("#bodySmsModal").fadeOut(20);
             document.getElementById('bodySmsStatusModal').style.visibility = 'visible';
             document.getElementById('bodySmsStatusModal').style.opacity = 1;
             $("#smsModal").fadeOut(2000);
             $("#loadingWaredover").fadeOut(2000);
         }
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeSMSModal(){
    $("#smsModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//FOR Archived Request SMS 
function doProcessArchivedSMS(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('smsArchivedModal').style.visibility = 'visible';
        document.getElementById('smsArchivedModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#smsArchivedModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissArchivedModalSMS() {
    $("#smsArchivedModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeArchivedSMSModal(){
    $("#smsArchivedModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//FOR SMS WARED ARCHIVE
function doProcessSMSWaredArchive(data){
      if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('smsWaredArchiveModal').style.visibility = 'visible';
        document.getElementById('smsWaredArchiveModal').style.opacity = 1;
        $("#loadingWaredover").fadeIn(20);
        $("#smsWaredArchiveModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}
function dimissModalSMSWaredArchive() {
    $("#smsWaredArchiveModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeSMSWaredArchiveModal(){
    $("#smsWaredArchiveModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//FOR SADER SMS 
function doProcessSaderSMS(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('smsSaderModal').style.visibility = 'visible';
        document.getElementById('smsSaderModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#smsSaderModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalSaderSMS() {
    $("#smsSaderModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeSMSSaderModal(){
    $("#smsSaderModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

//FOR SMS SADER ARCHIVE 
function doProcessSaderArchiveSMS(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('smsSaderArchiveModal').style.visibility = 'visible';
        document.getElementById('smsSaderArchiveModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#smsSaderArchiveModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalSaderArchiveSMS() {
    $("#smsSaderArchiveModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeSMSSaderArchiveModal(){
    $("#smsSaderArchiveModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}


//FOR SADER COORINATOR 
function doProcessCoordinator(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('saderCoodinatorModal').style.visibility = 'visible';
        document.getElementById('saderCoodinatorModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#saderCoodinatorModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalOutCoordinator() {
    $("#saderCoodinatorModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeSaderCoordinator(){
    $("#saderCoodinatorModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}


//FOR WARED COORINATOR 
function doProcessCoordinatorWared(data){
    if(data.status  == "begin") {
                document.getElementById('loadingmsg').style.visibility = 'visible';
                document.getElementById('loadingmsg').style.opacity = 1;
                document.getElementById('loadingWaredover').style.visibility = 'visible';
                document.getElementById('loadingWaredover').style.opacity = 1;
                $("#loadingmsg").fadeIn(20);
                $("#loadingWaredover").fadeIn(20);
      } else if (data.status == "success") {
        $("#loadingmsg").fadeOut(20);
        document.getElementById('loadingWaredover').style.visibility = 'visible';
        document.getElementById('loadingWaredover').style.opacity = 1;
        document.getElementById('waredCoordinatorModal').style.visibility = 'visible';
        document.getElementById('waredCoordinatorModal').style.opacity = 1;
        //$("#loadingWaredover").fadeIn(20);
        $("#waredCoordinatorModal").fadeIn(20);
        //$("#waredModal").modal('show');
    }
}

function dimissModalOutCoordinatorWared() {
    $("#waredCoordinatorModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    //document.getElementById("saderSearchForm:refreshMe2").click();
}
function closeWaredCoordinator(){
    $("#waredCoordinatorModal").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
}

function executeSaveWaredBtn(){
  document.getElementById("create:inCreate").click();   
}

function executeUploadfileWaredBtn(){
    document.getElementById("create:waredFile").click();
}

function executeSaveSaderBtn(){
  document.getElementById("createSader:outCreate").click();   
}

function executeUploadfileSaderBtn(){
    document.getElementById("createSader:saderFile").click();
}

function showMyModal(formDiv, data) {
   if (data.status == "success") {
    //Create mother of two Divs
    var motherDiv = document.getElementById("mother");
    var titleDiv = document.createElement("div");
    var contentDiv = document.getElementById(formDiv);
    var titleH3 = document.createElement("h3");
    var closeBtn = document.createElement("a");
    motherDiv.setAttribute("class","panel panel-default");
    motherDiv.setAttribute("dir", dir );
        titleDiv.setAttribute("id","titleMyModal");
        titleDiv.setAttribute("class", "panel-heading");
            titleH3.setAttribute("class","panel-title");
            titleH3.textContent = title;
        titleDiv.appendChild(titleH3);
        contentDiv.setAttribute("class", "panel-body");
            closeBtn.setAttribute("id","closeMyModal");
            closeBtn.setAttribute("href","#");
            closeBtn.setAttribute("class","btn btn-danger btn-lg btn-block");
            closeBtn.setAttribute("onclick","hideMyModal()");
            closeBtn.textContent = close;
        
        contentDiv.appendChild(closeBtn);
    motherDiv.appendChild(titleDiv);
    motherDiv.appendChild(contentDiv);
    
    //Show them
    document.getElementById('loadingWaredover').style.visibility = 'visible';
    document.getElementById('loadingWaredover').style.opacity = 1;
    document.getElementById('mother').style.visibility = 'visible';
    document.getElementById('mother').style.opacity = 1;
    $("#mother").fadeIn(20);
    $("#loadingWaredover").fadeIn(20);
   }
  
}

function hideMyModal(){
    $("#mother").fadeOut(20);
    $("#loadingWaredover").fadeOut(20);
    document.getElementById('loadingWaredover').style.visibility = 'hidden';
    document.getElementById('loadingWaredover').style.opacity = 0;
    document.getElementById('mother').style.visibility = 'hidden';
    document.getElementById('mother').style.opacity = 0;
    document.getElementById("mother").removeChild(document.getElementById("titleMyModal"));
    $('#closeMyModal').remove();
}

function closeMe() {
    $('#orderMessages').fadeOut(20);
    $('#orderBackground').fadeOut(20);
}

function closeMeAllMsg() {
    $('#allMsgModal').fadeOut(20);
    $('#msgBackground').fadeOut(20);
}

function closeMeProfitMsg(){
    $('profitMsgModal').fadeOut(20);
     $('#profitBackground').fadeOut(20);
}

function getFocus(data){
    if (data.status == "success") {
        document.getElementById("financailInfoLoanerTableForm:loanerNameInput").focus();
    }
}