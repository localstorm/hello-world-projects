var browserType;

if (document.layers) {
    browserType = "nn4"
}
if (document.all) {
    browserType = "ie"
}
if (window.navigator.userAgent.toLowerCase().match("gecko")) {
  browserType = "gecko"
}
if (window.opera) {
  browserType = "opera"
}

function focus(id)    
{
    var z=document.getElementById(id); 
    try {
        z.focus();
        z.select();
    } catch(e) {
    }
}


function hide(id) 
{
  if (browserType == "gecko" || browserType == "opera")
     document.poppedLayer = eval('document.getElementById("'+id+'")');
  else if (browserType == "ie")
     document.poppedLayer = eval('document.getElementById("'+id+'")');
  else
     document.poppedLayer = eval('document.layers["'+id+'"]');
  document.poppedLayer.style.display = "none";
}

function show(id) 
{
  if (browserType == "gecko" || browserType == "opera")
     document.poppedLayer = eval('document.getElementById("'+id+'")');
  else if (browserType == "ie")
     document.poppedLayer = eval('document.getElementById("'+id+'")');
  else
     document.poppedLayer = eval('document.layers["'+id+'"]');
  document.poppedLayer.style.display = "inline";
}

function show(id, focusId) 
{
  if (browserType == "gecko" || browserType == "opera")
     document.poppedLayer = eval('document.getElementById("'+id+'")');
  else if (browserType == "ie")
     document.poppedLayer = eval('document.getElementById("'+id+'")');
  else
     document.poppedLayer = eval('document.layers["'+id+'"]');
  document.poppedLayer.style.display = "inline";
  
  focus(focusId);
}



