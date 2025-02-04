let img;

let canvasX = 1280;
let canvasY = 720;

function preload() {
  img = loadImage("back.webp");
}

let SxSlider, SySlider, spanSlider, rotSlider;

function setup() {
  createCanvas(canvasX, canvasY, WEBGL);
  
  createSliderWithLabel("Sx: ", 0.1, 1.0, 0.5 / sqrt(2), 0.01, (slider) => (SxSlider = slider));
  createSliderWithLabel("Sy: ", 0.1, 1.0, 0.5 / sqrt(2), 0.01, (slider) => (SySlider = slider));
  createSliderWithLabel("Span: ", 0, PI, PI / 2, 0.01, (slider) => (spanSlider = slider));
  createSliderWithLabel("Rot: ", 0, 2*PI, PI / 4, 0.01, (slider) => (rotSlider = slider));
  
}

function draw(){
  background(200);
  
  let Sx = SxSlider.value();
  let Sy = SySlider.value();
  let span = spanSlider.value();
  let rot = rotSlider.value();
  
  
  beginShape();
  texture(img);
  
  // T = { {Sx*cos(rotate), Sy*cos(span+rotate)},  {Sx*sin(rotate), Sy*sin(span+rotate)}}
  // x' = x*Sx*cos(rotate) + y*Sy*cos(span+rotate);
  // y' = x*Sx*sin(rotate) + y*Sy*sin(span+rotate);
  // Sx Sy: scaling factor
  // span: the angle between new x and y axis
  // rot: the rotation angle to new coordinate
  
  vertex(newCoorX(-canvasX/2,-canvasY/2,Sx,Sy,span,rot),newCoorY(-canvasX/2,-canvasY/2,Sx,Sy,span,rot), 0, 0, 0); // Top-left corner
  vertex(newCoorX(canvasX/2,-canvasY/2,Sx,Sy,span,rot),newCoorY(canvasX/2,-canvasY/2,Sx,Sy,span,rot), 0, 0, img.width, 0); // Top-right corner
  vertex(newCoorX(canvasX/2,canvasY/2,Sx,Sy,span,rot),newCoorY(canvasX/2,canvasY/2,Sx,Sy,span,rot), img.width, img.height); // Bottom-right corner
  vertex(newCoorX(-canvasX/2,canvasY/2,Sx,Sy,span,rot),newCoorY(-canvasX/2,canvasY/2,Sx,Sy,span,rot), 0, 0, img.height);
  endShape(CLOSE);
}

function newCoorX(x, y, Sx, Sy, span, rot){
  let newX = x*Sx*cos(rot) + y*Sy*cos(span+rot);
  let newY = x*Sx*sin(rot) + y*Sy*sin(span+rot);
  return newX;
}

function newCoorY(x, y, Sx, Sy, span, rot){
  let newX = x*Sx*cos(rot) + y*Sy*cos(span+rot);
  let newY = x*Sx*sin(rot) + y*Sy*sin(span+rot);
  return newY;
}

function createSliderWithLabel(labelText, min, max, defaultValue, step, callback) {
  let container = createDiv();
  container.style("margin", "5px");
  let label = createSpan(labelText);
  label.style("margin-right", "5px");
  
  let slider = createSlider(min, max, defaultValue, step);
  slider.style("width", "200px");

  container.child(label);
  container.child(slider);

  callback(slider);
}