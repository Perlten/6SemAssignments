const GRID_SIZE = 40;
const WIDTH = 800;
const HEIGHT = 800;
const NODES_IN_ROW = (WIDTH / GRID_SIZE) + 1;

let startNode = null;
let endNode = null;

nodeArray = [];

let eventContainer;


function handleAStart() {
  for (let node of nodeArray) {
    node.visited = false;
    node.globalGoal = Infinity;
    node.localGoal = Infinity;
    node.parrent = null;
    node.isLine = false;
  }

  currentNode = startNode;
  startNode.localGoal = 0;
  startNode.globalGoal = heuristic(startNode, endNode);

  nodesNotTested = [startNode];
  while (nodesNotTested.length > 0 && currentNode != endNode) {
    nodesNotTested.sort((a, b) => a.globalGoal - b.globalGoal);

    while (nodesNotTested.length > 0 && nodesNotTested[0].visited) {
      nodesNotTested.shift();
    }

    if (nodesNotTested.length < 1) {
      break;
    }

    currentNode = nodesNotTested[0];
    currentNode.visited = true;

    for (let neighbour of currentNode.neighbourNodes) {

      if (!neighbour.visited && !neighbour.isObstacle) {
        nodesNotTested.push(neighbour);
      }

      let possibleLowerDistance = currentNode.localGoal + distance(currentNode, neighbour);
      if (possibleLowerDistance < neighbour.localGoal) {
        neighbour.parrent = currentNode;
        neighbour.localGoal = possibleLowerDistance;

        neighbour.globalGoal = neighbour.localGoal + heuristic(neighbour, endNode);
      }
    }
  }
}

function setup() {
  eventContainer = new EventContainer();
  createCanvas(WIDTH, HEIGHT);
  setupGrid();
  connectNodes();
  startNode = nodeArray[0];
  endNode = nodeArray[GRID_SIZE - 1];

  handleAStart();
}

function distance(nodeA, nodeB) {
  let triangleWidth = Math.abs(nodeA.x - nodeB.x);
  let triangleHeight = Math.abs(nodeA.y - nodeB.y);
  return Math.sqrt(Math.pow(triangleWidth, 2) + Math.pow(triangleHeight, 2));
}

function heuristic(nodeA, nodeB) {
  return distance(nodeA, nodeB);
  // return 1;
}

function draw() {
  eventContainer.handleEvents();
  for (let node of nodeArray) {
    node.draw();
  }

  if (endNode.parrent) {
    let currentNode = endNode.parrent;
    while (currentNode != startNode) {
      currentNode.isLine = true;
      currentNode = currentNode.parrent;
    }
  }
}

function mouseClicked() {
  let index = getIndexFromCordinate(mouseX, mouseY);
  if (eventContainer.ctrlPressed) {
    endNode = nodeArray[index];
  } else if (eventContainer.shiftPressed) {
    startNode = nodeArray[index];
  } else {
    nodeArray[index].isObstacle = !nodeArray[index].isObstacle;
  }
  handleAStart();
}



function setupGrid() {
  for (let y = 0; y < HEIGHT; y += GRID_SIZE) {
    for (let x = 0; x < WIDTH; x += GRID_SIZE) {
      nodeArray.push(new Node(x, y));
    }
  }
}

function connectNodes() {
  let i = 0;
  for (let y = 0; y < HEIGHT; y += GRID_SIZE) {
    for (let x = 0; x < WIDTH; x += GRID_SIZE) {
      let currentIndex = getIndexFromCordinate(x, y);
      let currentNode = nodeArray[currentIndex];
      if (y > 0) {
        let aboveIndex = currentIndex - NODES_IN_ROW;
        currentNode.neighbourNodes.push(nodeArray[aboveIndex + 1]);
      }
      if (y < HEIGHT - GRID_SIZE) {
        let belowIndex = currentIndex + NODES_IN_ROW - 1;
        currentNode.neighbourNodes.push(nodeArray[belowIndex]);
      }
      if (x > 0) {
        let leftIndex = currentIndex - 1;
        currentNode.neighbourNodes.push(nodeArray[leftIndex]);
      }
      if (x < WIDTH - GRID_SIZE) {
        let rightIndex = currentIndex + 1;
        currentNode.neighbourNodes.push(nodeArray[rightIndex]);
      }
    }
  }
}

function getIndexFromCordinate(x, y) {
  let NODES_IN_ROW = (WIDTH / GRID_SIZE);
  let xTemp = Math.floor(x / GRID_SIZE);
  let yTemp = Math.floor(y / GRID_SIZE);
  let index = (yTemp * NODES_IN_ROW) + xTemp
  return index;
}

class Node {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.parrent = null;
    this.isObstacle = false;
    this.localGoal = Infinity;
    this.globalGoal = Infinity;
    this.visited = false;
    this.neighbourNodes = [];
    this.isLine = false;
  }


  draw() {
    stroke(0, 0, 255);
    if (this.isObstacle) {
      fill(0, 0, 0);
    } else if (this == startNode) {
      fill(0, 255, 0);
    } else if (this == endNode) {
      fill(255, 0, 0);
    } else if (this.visited) {
      fill(0, 0, 255);
    } else {
      fill(255, 255, 255);
    }

    if (this.isLine) {
      fill(255, 255, 0);
    }

    rect(this.x, this.y, GRID_SIZE, GRID_SIZE);
  }
}


class EventContainer {
  constructor() {
    this.shiftPressed = false;
    this.ctrlPressed = false;
  }

  handleEvents() {
    this.ctrlPressed = keyIsDown(17);
    this.shiftPressed = keyIsDown(16);
  }
}