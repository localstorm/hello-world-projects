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
  $('#'+id).hide('medium');
}


function show(id) 
{
  $('#'+id).show('medium');
}

function show(id, focusId) 
{
  $('#'+id).show('medium');
  focus(focusId);
}



