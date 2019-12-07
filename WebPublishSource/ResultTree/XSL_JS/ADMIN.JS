var xmlDoc;
var selectedEntity;

var appState = new applicationState()

// Function to deal with Displaying the Fields in a Rule Element
function applicationState() 
{
  this.contextMenu = null;
}

function clean() 
{
  var contextMenu
  
  // remove and destroy context menu
  if(appState.contextMenu != null) 
  {
    contextMenu = appState.contextMenu.removeNode(true);
    contextMenu = null;
    appState.contextMenu = null;
  }
}

function returnContainer(container) 
{

  while(container.tagName != "DIV") 
  {
    container = container.parentNode  
  }
  return container
}


function initVerfRules() 
{
  var xslDoc

  xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;

  xslDoc = new ActiveXObject('MSXML2.DOMDocument');
  xslDoc.async = false;


  xmlDoc.load("tree/tree.xml");
  xslDoc.load("xsl_js/ApplyIDToTree.xslt");
  
  xmlDoc.loadXML(xmlDoc.documentElement.transformNode(xslDoc));
  
  xslDoc.load("xsl_js/ProcessTree.xslt");

  folderTree.innerHTML = xmlDoc.documentElement.transformNode(xslDoc);
}

function insertUpdateDisplay(action) 
{
  var xslDoc
  var xslTemplate;
  var xslProc;
  var entity;

  xslDoc = new ActiveXObject('MSXML2.FreeThreadedDOMDocument')
  xslDoc.async = false;

  xslTemplate = new ActiveXObject('MSXML2.XSLTemplate')

  xslDoc.load("xsl_js/insertUpdate.xslt");
  xslTemplate.stylesheet = xslDoc;
  xslProc = xslTemplate.createProcessor();
  entity = xmlDoc.documentElement.selectSingleNode("//entity[@id='" + selectedEntity +"']");
  xslProc.input = entity;

  xslProc.addParameter("action", action);
  xslProc.addParameter("selectedEntity", selectedEntity);

  xslProc.transform();
  
  content.innerHTML = xslProc.output;
}



function displayError(msg, duration) 
{
  divError.innerText = msg;
  alert( msg );
  setTimeout("divError.innerText = ''", duration);
}

function clickOnEntity(entity) 
{

  if(entity.open == "false") 
  {
    expand(entity, true);
  }
  else 
  {
    collapse(entity);
  }

  clean();
  window.event.cancelBubble = "true"
}

function expand(entity) 
{
  var oImage

  oImage = entity.childNodes(0).all[entity.id + "Image"]
  oImage.src = entity.imageOpen

  for(i=0; i < entity.childNodes.length; i++) 
  {
    if(entity.childNodes(i).tagName == "DIV") 
    {
      entity.childNodes(i).style.display = "block"
    }
  }
  entity.open = "true"
}

function collapse(entity) 
{
  var oImage
  var i
  
  if(entity.id != "folderTree") 
  {
    oImage = entity.childNodes(0).all[entity.id + "Image"]
    oImage.src = entity.image
  }

  for(i=0; i < entity.childNodes.length; i++) 
  {
    if(entity.childNodes(i).tagName == "DIV" && entity.childNodes(i).id != "divError") 
    {
      if(entity.id != "folderTree") 
      {
        entity.childNodes(i).style.display = "none"
      }
      collapse(entity.childNodes(i))
    }
  }
  entity.open = "false"
  if(entity.id == "folderTree") 
  {
     content.innerHTML ="";
  }
}

function expandAll(entity) 
{
  var oImage
  var i

  if(entity.id != "folderTree") expand(entity, false)

  for(i=0; i < entity.childNodes.length; i++) 
  {
    if(entity.childNodes(i).tagName == "DIV" && entity.childNodes(i).id != "divError") 
    {
      expandAll(entity.childNodes(i))
    }
  }
}

function redirect(url) 
{
  if(url != '') 
  {
    document.location = url;
  }
}


// Function to deal with swaping CSS classes
function swapClass(obj, cls) {
  obj.className = cls
}

// Function to deal with swaping CSS classes
function returnFalse() {
  return false;
}


