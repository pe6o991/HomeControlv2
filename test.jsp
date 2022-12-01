<%@ page import="com.auth"%>
<%@ page import="com.main"%>
<%@ page import="com.device"%>
<html>
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="resources/stylesheets/color.css">
		<script src='//cdnjs.cloudflare.com/ajax/libs/gsap/latest/TweenMax.min.js'></script>
		<script src='//cdnjs.cloudflare.com/ajax/libs/gsap/latest/utils/Draggable.min.js'></script>
		<script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
		<script src="resources/scripts/common.js"></script>
		<script src="resources/scripts/index.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
		<script src="https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-1b93190375e9ccc259df3a57c1abc0e64599724ae30d7ea4c6877eb615f89387.js"></script>
		
		<style>
		
body{
	background-color:var(--background-color);
}

.menu{
	height:fit-content;
	align-content:center;
	padding:10px;
}

#options{
	border:none;
	border-radius:0.1em;
	font-size:3rem;
	background-color:var(--button-color);
	color:var(--text-color);
}

#options:hover{
	background-color:var(--text-color);
	color:var(--background-color);
}

.navlink{
	background-color:var(--button-color);
	margin:0.6rem;
	font-size:3rem;
	text-decoration:none;
	border-radius:0.1em;
	padding:0.05em;	
	color:var(--text-color);
}

.navlink:hover{
	background-color:var(--text-color);
	color:var(--background-color);
}

.content{
	background-color:var(--secondary-color);
	width:100%;
	height:100%;
	border-radius:0.3rem;
	display:block;
	
	
}

.spin{
	font-size:20rem;
	position:relative;
	top:45%;
	left:50%;
}

.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0,0,0); /* Fallback color */
	background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	opacity:1;
	animation:visible 2s;
	}

		    /* Modal Content/Box */
.modal-content {
	background-color: var(--background-color);
	margin: 15% auto; /* 15% from the top and centered */
	padding: 20px;
	border: 1px solid #888;
	width: 80%; /* Could be more or less, depending on screen size */
	}

	@keyframes visible{
		from{
			opacity:0;
		}
		to{
			opacity:1;
		}
	}
	
.modal-content > p{
	text-align:center;
	color:var(--text-color);
	font-size:1.5rem;
	font-family:arial;
}

.close {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
	}

.close:hover,
.close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
	}
	
.theme1{
			background:url('./resources/images/default.png');
		}
		.theme2{
			background:url('./resources/images/coolgray.png');
		}
		.theme3{
			background:url('./resources/images/warmgray.png');
		}
		.theme4{
			background:url('./resources/images/bluegray.png');
		}
		
		#volts{
			width:25;
		}
		#amps{
			width:50%;
		}
		#power{
			width:10%;
		}
		#energy{
			width:77%;
		}
/* DEVICES STYLE*/
	.TempSensor{
			background-color:var(--button-color);
			color:var(--text-color);
			display:inline-block;
			position:absolute;
			width:fit-content;
			height:fit-content;
			margin:10px;
			border-radius:0.3rem;
		}
		
		.PowerSensor{
			background-color:var(--button-color);
			color:var(--text-color);
			display:inline-block;
			position:absolute;
			width:fit-content;
			height:fit-content;
			margin:10px;
			border-radius:0.3rem;
		}
		
		.SystemMonitor{
			background-color:var(--button-color);
			color:var(--text-color);
			display:inline-block;
			position:absolute;
			width:fit-content;
			height:150px;
			margin:10px;
			border-radius:0.3rem;
		}
		
		.IrControl{
			background-color:var(--button-color);
			color:var(--text-color);
			display:inline-block;
			position:absolute;
			width:fit-content;
			height:fit-content;
			margin:10px;
			border-radius:0.3rem;
		}
		
		.RelayControl{
			background-color:var(--button-color);
			color:var(--text-color);
			display:inline-block;
			position:absolute;
			width:fit-content;
			height:fit-content;
			margin:10px;
			border-radius:0.3rem;
		}

		.TempSensor > div{
			display:flex;
			padding:2px;
		}

		.TempSensor > .title{
			display:block;
			text-align:center;
			font-size:1.5rem;
			padding:5px;
		}

		.TempSensor > div > .value{
			background-color:var(--background-color);
			display:inliine;
			width:100%;
		}

		.TempSensor > div > .value > span{
			position:absolute;
		}

		.TempSensor > div > .value > .bar{
			background-color:var(--secondary-color);
			width:40%;
			height:100%;
		}

		.TempSensor > div > span{
			display:block;
			text-align:left;
			width:130px;
		}
		#temp{
			width:25;
		}
		#humidity{
			width:50%;
		}
		#pressure{
			width:10%;
		}

		.PowerSensor > div{
			display:flex;
			padding:2px;
		}

		.PowerSensor > .title{
			display:block;
			text-align:center;
			font-size:1.5rem;
			padding:5px;
		}

		.PowerSensor > div > .value{
			background-color:var(--background-color);
			display:inliine;
			width:100%;
		}

		.PowerSensor > div > .value > span{
			position:absolute;
		}

		.PowerSensor > div > .value > .bar{
			background-color:var(--secondary-color);
			width:40%;
			height:100%;
		}

		.PowerSensor > div > span{
			display:block;
			text-align:left;
			width:130px;
		}

		.SystemMonitor > div{
			display:flex;
			padding:2px;
		}

		.SystemMonitor > .title{
			display:block;
			text-align:center;
			font-size:1.5rem;
			padding:5px;
		}

		.SystemMonitor > div > .value{
			background-color:var(--background-color);
			display:inliine;
			width:100%;
		}

		.SystemMonitor > div > .value > span{
			position:absolute;
		}

		.SystemMonitor > div > .value > .bar{
			background-color:var(--secondary-color);
			width:40%;
			height:100%;
		}

		.SystemMonitor > div > span{
			display:block;
			text-align:left;
			width:130px;
		}
		
		.IrControl > div{
			display:flex;
			padding:2px;
			justify-content:center;
		}
		.IrControl > .title{
			display:block;
			text-align:center;
			font-size:1.5rem;
			padding:5px;
		}
		
		.IrControl > div > span > button{
			border:none;
			border-radius:0.1em;
			font-size:1.5rem;
			background-color:var(--background-color);
			color:var(--text-color);
			margin:2px;
		}
		.IrControl > div > span > button:hover{
			background-color:var(--text-color);
			color:var(--background-color);
		}
		
		
		.RelayControl > div > span > button{
			border:none;
			border-radius:0.1em;
			font-size:1.5rem;
			background-color:var(--button-color);
			color:var(--text-color);
			margin:2px;
		}
		
		.RelayControl > div > span > button:hover{
			background-color:var(--text-color);
			color:var(--button-color);
		}
		
		.RelayControl > div{
			display:flex;
			padding:2px;
		}
		
		.RelayControl > .title{
			display:block;
			text-align:center;
			font-size:1.5rem;
			padding:5px;
		}

		</style>
	</head>
	<body onload="loaded()">
		<% 
			String address = request.getRemoteAddr();
			String ip[] = address.split("\\.",4);
			boolean authenticated=false;
			if(!ip[0].equals("192") & !ip[1].equals("168")){
				for(String val : auth.authenticated.keySet()){
					if(address.equals(val)) authenticated=true;
				}
				if(authenticated==false){
					response.sendRedirect("auth.jsp");
				}	
			}
		%>

		<div class="menu">
			<a class="navlink" href="devices.jsp">Devices</a>
			<a class="navlink" href="#">Scripts</a>
			<a class="navlink" href="#" id="redir">Statistics</a>
			<button id="options"><i class='fa fa-gear'></i></button>
		</div>
		
		<div id="content" class="content">
			<%      
			for(device dev : main.devices){
				out.write(dev.toHTML());
			}
			%>
		</div>
<script>
var rowSize   = 100;
var colSize   = 100;
var gutter    = 7;     // Spacing between tiles
   // Number of tiles to initially populate the grid with
var fixedSize = false; // When true, each tile's colspan will be fixed to 1
var oneColumn = false; // When true, grid will only have 1 column and tiles have fixed colspan of 1
var threshold = "50%"; // This is amount of overlap between tiles needed to detect a collision

var $list = $("#content");

// Live node list of tiles
var tiles  = $list[0].getElementsByClassName("tile");
var label  = 1;
var zIndex = 1000;
var numTiles  = $list[0].children.length;

var startWidth  = "100%";
var startSize   = colSize;
var singleWidth = colSize * 3;

var colCount   = null;
var rowCount   = null;
var gutterStep = null;

var shadow1 = "0 1px 3px  0 rgba(0, 0, 0, 0.5), 0 1px 2px 0 rgba(0, 0, 0, 0.6)";
var shadow2 = "0 6px 10px 0 rgba(0, 0, 0, 0.3), 0 2px 2px 0 rgba(0, 0, 0, 0.2)";

$(window).resize(resize);

init();

// ========================================================================
//  INIT
// ========================================================================
function init() {

    var width = startWidth;
    
            fixedSize = false;
            oneColumn = false;
            colSize   = startSize;
      
    // For images demo
    //window.stop();

   // $(".tile").each(function() {
    //    Draggable.get(this).kill();
    //    $(this).remove();
   // });

    TweenLite.to($list, 0.2, { width: width });
    TweenLite.delayedCall(0.55, populateBoard);

    function populateBoard() {

        label = 1;
        resize();

        for (var i = 0; i < numTiles; i++) {
            createTile(i);
        }
    }
}


// ========================================================================
//  RESIZE
// ========================================================================
function resize() {

    colCount   = oneColumn ? 1 : Math.floor($list.outerWidth() / (colSize + gutter));
    gutterStep = colCount == 1 ? gutter : (gutter * (colCount - 1) / colCount);
    rowCount   = 0;

    layoutInvalidated();
}


// ========================================================================
//  CHANGE POSITION
// ========================================================================
function changePosition(from, to, rowToUpdate) {

    var $tiles = $(".tile");
    var insert = from > to ? "insertBefore" : "insertAfter";

    // Change DOM positions
    $tiles.eq(from)[insert]($tiles.eq(to));

    layoutInvalidated(rowToUpdate);
}

function createTile(num) {

    var colspan = fixedSize || oneColumn ? 1 : Math.floor(Math.random() * 2) + 1;
    //var element = $("<div></div>").addClass("tile").html(label++);
	var element = document.getElementsByClassName('content')[0].children[num];
	element.classList.add("tile");
    var lastX   = 0;

    Draggable.create(element, {
        onDrag      : onDrag,
        onPress     : onPress,
        onRelease   : onRelease,
        zIndexBoost : false
    });

    // NOTE: Leave rowspan set to 1 because this demo 
    // doesn't calculate different row heights
    var tile = {
        col        : null,
        colspan    : colspan,
        height     : 0,
        inBounds   : true,
        index      : null,
        isDragging : false,
        lastIndex  : null,
        newTile    : true,
        positioned : false,
        row        : null,
        rowspan    : 2, 
        width      : 0,
        x          : 0,
        y          : 0
    };

    // Add tile properties to our element for quick lookup
    element.tile = tile;

    //$list.append(element);
    layoutInvalidated();

    function onPress() {

        lastX = this.x;
        tile.isDragging = true;
        tile.lastIndex  = tile.index;

        TweenLite.to(element, 0.2, {
            autoAlpha : 0.75,
            boxShadow : shadow2,
            scale     : 0.95,
            zIndex    : "+=1000"
        });
    }

    function onDrag() {

        // Move to end of list if not in bounds
        if (!this.hitTest($list, 0)) {
            tile.inBounds = false;
            changePosition(tile.index, tiles.length - 1);
            return;
        }

        tile.inBounds = true;

        for (var i = 0; i < tiles.length; i++) {

            // Row to update is used for a partial layout update
            // Shift left/right checks if the tile is being dragged 
            // towards the the tile it is testing
            var testTile    = tiles[i].tile;
            var onSameRow   = (tile.row === testTile.row);
            var rowToUpdate = onSameRow ? tile.row : -1;
            var shiftLeft   = onSameRow ? (this.x < lastX && tile.index > i) : true;
            var shiftRight  = onSameRow ? (this.x > lastX && tile.index < i) : true;
            var validMove   = (testTile.positioned && (shiftLeft || shiftRight));

            if (this.hitTest(tiles[i], threshold) && validMove) {
                changePosition(tile.index, i, rowToUpdate);
                break;
            }
        }

        lastX = this.x;
    }

    function onRelease() {

        // Move tile back to last position if released out of bounds
        this.hitTest($list, 0)
            ? layoutInvalidated()
        : changePosition(tile.index, tile.lastIndex);

        TweenLite.to(element, 0.2, {
            autoAlpha : 1,
            boxShadow: shadow1,
            scale     : 1,
            x         : tile.x,
            y         : tile.y,
            zIndex    : ++zIndex
        });

        tile.isDragging = false;
    }
}

// ========================================================================
//  LAYOUT INVALIDATED
// ========================================================================
function layoutInvalidated(rowToUpdate) {

    var timeline = new TimelineMax();
    var partialLayout = (rowToUpdate > -1);

    var height = 0;
    var col    = 0;
    var row    = 0;
    var time   = 0.35;

    $(".tile").each(function(index, element) {

        var tile    = this.tile;
        var oldRow  = tile.row;
        var oldCol  = tile.col;
        var newTile = tile.newTile;

        // PARTIAL LAYOUT: This condition can only occur while a tile is being 
        // dragged. The purpose of this is to only swap positions within a row, 
        // which will prevent a tile from jumping to another row if a space
        // is available. Without this, a large tile in column 0 may appear 
        // to be stuck if hit by a smaller tile, and if there is space in the 
        // row above for the smaller tile. When the user stops dragging the 
        // tile, a full layout update will happen, allowing tiles to move to
        // available spaces in rows above them.
        if (partialLayout) {
            row = tile.row;
            if (tile.row !== rowToUpdate) return;
        }

        // Update trackers when colCount is exceeded 
        if (col + tile.colspan > colCount) {
            col = 0; row++;
        }

        $.extend(tile, {
            col    : col,
            row    : row,
            index  : index,
            x      : col * gutterStep + (col * colSize),
            y      : row * gutterStep + (row * rowSize),
            width  : tile.colspan * colSize + ((tile.colspan - 1) * gutterStep),
            height : tile.rowspan * rowSize
        });

        col += tile.colspan;

        // If the tile being dragged is in bounds, set a new
        // last index in case it goes out of bounds
        if (tile.isDragging && tile.inBounds) {
            tile.lastIndex = index;
        }

        if (newTile) {

            // Clear the new tile flag
            tile.newTile = false;

            var from = {
                autoAlpha : 0,
                boxShadow : shadow1,
                height    : tile.height,
                scale     : 0,
                width     : tile.width
            };

            var to = {
                autoAlpha : 1,
                scale     : 1,
                zIndex    : zIndex
            }

            timeline.fromTo(element, time, from, to, "reflow");
        }

        // Don't animate the tile that is being dragged and
        // only animate the tiles that have changes
        if (!tile.isDragging && (oldRow !== tile.row || oldCol !== tile.col)) {

            var duration = newTile ? 0 : time;

            // Boost the z-index for tiles that will travel over 
            // another tile due to a row change
            if (oldRow !== tile.row) {
                timeline.set(element, { zIndex: ++zIndex }, "reflow");
            }

            timeline.to(element, duration, {
                x : tile.x,
                y : tile.y,
                onComplete : function() { tile.positioned = true; },
                onStart    : function() { tile.positioned = false; }
            }, "reflow");
        }
    });

    if (!row) rowCount = 1;

    // If the row count has changed, change the height of the container
    if (row !== rowCount) {
        rowCount = row;
        height   = rowCount * gutterStep + (++row * rowSize);
        timeline.to($list, 0.2, { height: height }, "reflow");
    }
}

</script>
		<div id="myModal" class="modal">
		<div id="modal-content" class="modal-content">
			<span class="close">&times;</span>
			<p>SETTINGS</p>
			<button class="theme theme1" onclick="Default()">Default</button>
			<button class="theme theme2" onclick="CoolGray()">CoolGray</button>
			<button class="theme theme3" onclick="WarmGray()">WarmGray</button>
			<button class="theme theme4" onclick = "BlueGray()">BlueGray</button>
			<button onclick = "loadHTML()">LoadHTML</button>
			<button id="test">Test</button>
		</div>
		</div>
	</body>
</html>