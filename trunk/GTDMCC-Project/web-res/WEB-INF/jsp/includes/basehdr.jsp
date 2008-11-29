<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mission-Control Center</title>
<link rel="alternate stylesheet" type="text/css" media="all" href="<c:url value="/css/calendar-blue2.css"/>" title="blue">
<link href="<c:url value="/css/style.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/lang/calendar-en.js"/>"></script>
<script language="javascript1.2" src="<c:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">

var oldLink = null;
// code to change the active stylesheet
function setActiveStyleSheet(link, title) {
  var i, a, main;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
      a.disabled = true;
      if(a.getAttribute("title") == title) a.disabled = false;
    }
  }
  if (oldLink) oldLink.style.fontWeight = 'normal';
  oldLink = link;
  link.style.fontWeight = 'bold';
  return false;
}

// This function gets called when the end-user clicks on some date.
function selected(cal, date) {
  cal.sel.value = date; // just update the date in the input field.
  if (cal.dateClicked && (cal.sel.id == "sel1" || cal.sel.id == "sel3"))
    // if we add this call we close the calendar on single-click.
    // just to exemplify both cases, we are using this only for the 1st
    // and the 3rd field, while 2nd and 4th will still require double-click.
    cal.callCloseHandler();
}

// And this gets called when the end-user clicks on the _selected_ date,
// or clicks on the "Close" button.  It just hides the calendar without
// destroying it.
function closeHandler(cal) {
  cal.hide();                        // hide the calendar
//  cal.destroy();
  _dynarch_popupCalendar = null;
}

// This function shows the calendar under the element having the given id.
// It takes care of catching "mousedown" signals on document and hiding the
// calendar if the click was outside.
function showCalendar(id, format, showsTime, showsOtherMonths) {
  var el = document.getElementById(id);
  if (_dynarch_popupCalendar != null) {
    // we already have some calendar created
    _dynarch_popupCalendar.hide();                 // so we hide it first.
  } else {
    // first-time call, create the calendar.
    var cal = new Calendar(1, null, selected, closeHandler);
    // uncomment the following line to hide the week numbers
    // cal.weekNumbers = false;
    if (typeof showsTime == "string") {
      cal.showsTime = true;
      cal.time24 = (showsTime == "24");
    }
    if (showsOtherMonths) {
      cal.showsOtherMonths = true;
    }
    _dynarch_popupCalendar = cal;                  // remember it in the global var
    cal.setRange(1900, 2070);        // min/max year allowed.
    cal.create();
  }
  _dynarch_popupCalendar.setDateFormat(format);    // set the specified date format
  _dynarch_popupCalendar.parseDate(el.value);      // try to parse the text in field
  _dynarch_popupCalendar.sel = el;                 // inform it what input field we use

  // the reference element that we pass to showAtElement is the button that
  // triggers the calendar.  In this example we align the calendar bottom-right
  // to the button.
  _dynarch_popupCalendar.showAtElement(el.nextSibling, "Br");        // show the calendar

  return false;
}

var MINUTE = 60 * 1000;
var HOUR = 60 * MINUTE;
var DAY = 24 * HOUR;
var WEEK = 7 * DAY;

function isDisabled(date) {
  var today = new Date();
  return (Math.abs(date.getTime() - today.getTime()) / DAY) > 10;
}

function flatSelected(cal, date) {
  var el = document.getElementById("preview");
  el.innerHTML = date;
}


</script>
</head>
<body>

<script type="text/javascript">
setActiveStyleSheet(this, 'blue');
</script>

<div id="topPan">
	<a href="<c:url value="/" />"><img src="<c:url value="/images/logo.png" />" alt="Mission-Control Center" width="245" height="37" border="0"  class="logo" title="Mission-Control Center"/></a>
	<p>Be aware!</p>

  <div id="topContactPan" ></div>

  <div id="topMenuPan">
    <div id="topMenuLeftPan"></div>
    <div id="topMenuMiddlePan">
        <ul>
            <li class="home"><a href="<c:url value="/" />">Home</a></li>
            <li><a href="<c:url value="/actions/Logout" />">Log out</a></li>
            <li><a href="<c:url value="/actions/Configure" />">Configure</a></li>
            <li><a href="<c:url value="/actions/ViewAssets" />">Cash Flow</a></li>
            <li><a href="#">University</a></li>
            <li><a href="#">Resume</a></li>
            <li><a href="#">Blog</a></li>
            <li class="contact"><a href="#">Contact</a></li>
	</ul>
    </div>
    <div id="topMenuRightPan"></div>
  </div>
</div>
<div id="bodyPan">