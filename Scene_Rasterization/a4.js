import { Mat4 } from './math.js';
import { Parser } from './parser.js';
import { Scene } from './scene.js';
import { Renderer } from './renderer.js';
import { TriangleMesh } from './trianglemesh.js';
// DO NOT CHANGE ANYTHING ABOVE HERE


//############################################################
// Assignment 4 for CMPT 361 SFU
// Writtien by: Luukas Suomi, 301443184
// Due by: Dec,4,2022
//
// Description:
// Contains implementations of functions that create polygon meshes for both a cube and a sphere.
// The blinn-phong shading model was used to light the scene.
// 2D textures were also mapped to the 3D objects using the UV encoding scheme 
//##############################################################


//C:\Users\Luukas\OneDrive\Desktop\MATLAB\cmpt361_a4
//python3 -m http.server
//http://localhost:8000/a4.html


////////////////////////////////////////////////////////////////////////////////
// TODO: Implement createCube, createSphere, computeTransformation, and shaders
////////////////////////////////////////////////////////////////////////////////

//!!! NOTE !!!!!!!!!!!!!!!!!!
//The following source was used as reference in the consturction of the following two functions and subsequently the "createSphere" function
// http://www.songho.ca/opengl/gl_sphere.html
//accessed nov, 24, 2022
function spherexyz(index1,index2,stackH,secH){
  let anglestack, anglesec;
  let x,y,z,xy;

  anglestack = (Math.PI / 2 - index1 * stackH);
  xy = Math.cos(anglestack);
  z = Math.sin(anglestack);

  anglesec = -(index2 * secH);
  x = xy * Math.cos(anglesec);
  y = xy * Math.sin(anglesec);

  return [x,y,z];

}
function sphereindices(index,numsec){
  let k1 = index * (numsec + 1);
  let k2 = k1 + numsec + 1;

  return [k1,k2];
}
//!!!!!!!!!!!!!!!!!!!!

// Example two triangle quad
const quad = {
  positions: [
    -1,-1,-1
    ,1,-1,-1
    ,1,1,-1
    ,-1,-1,-1
    ,1,1,-1
    , 1,1,-1],
  normals: [
     0,0,1
    ,0,0,1
    ,0,0,1
    ,0,0,1
    ,0,0,1
    ,0,0,1
  ],
  uvCoords: [
     0, 0
    ,1, 0
    ,1, 1
    ,0, 0
    ,1, 1
    ,0, 1
  ]
}


//                back(1,1,-1)
//        ___________
//       /|        / |
//      /_|_______/  |
//     |  |       |  |
//     |  |_______|__|
//     | /        | /
//     |/_________|/
// (-1,-1,1)front
//
//The following function populates all of the three fields (matrices/arrays) to accurately create the cube above
TriangleMesh.prototype.createCube = function() {
  // TODO: populate unit cube vertex positions, normals, and uv coordinates
this.positions = [
  //##### FRONT #####  Number: 1
  
      1,-1,1 //top  left  front (0,1)
      ,-1,1,1 //bottom  right  front (1/2,2/3)
      ,-1,-1,1 //bottom  left  front (0,2/3)
        
      ,1,-1,1 //top  left  front (0,1)
      ,-1,1,1 //bottom  right  front (1/2,2/3)
      ,1,1,1 //top  right  front (1/2,1)
  
  //##### TOP ##### Number: 3
  
      ,1,-1,-1 
      ,1,1,1 
      ,1,-1,1 

      ,1,-1,-1 
      ,1,1,1 
      ,1,1,-1 
  
  //##### RIGHT ##### Number: 2
  
      ,1,1,1 
      ,-1,1,-1 
      ,-1,1,1 

      ,1,1,1 
      ,-1,1,-1 
      ,1,1,-1 

  //###### BOTTOM ##### Number: 4
  
      ,-1,-1,-1 
      ,-1,1,1 
      ,-1,-1,1 
        
      ,-1,-1,-1 
      ,-1,1,1 
      ,-1,1,-1 
  
  //##### LEFT ##### Number: 5 
  
      ,1,-1,1 
      ,-1,-1,-1 
      ,-1,-1,1 
        
      ,1,-1,1 
      ,-1,-1,-1 
      ,1,-1,-1 
  
  
  //####### BACK ####### Number: 6
  
      ,1,-1,-1 
      ,-1,1,-1 
      ,-1,-1,-1 
      
      ,1,-1,-1 
      ,-1,1,-1 
      ,1,1,-1 
      
    ];
    

  this.normals = [
//##### FRONT ##### 
0,0,1
,0,0,1
,0,0,1
,0,0,1
,0,0,1
,0,0,1  
//##### TOP ##### 
,1,0,0
,1,0,0
,1,0,0
,1,0,0
,1,0,0
,1,0,0
//##### RIGHT #####
,0,1,0
,0,1,0
,0,1,0
,0,1,0
,0,1,0
,0,1,0
//###### BOTTOM #####
,-1,0,0
,-1,0,0
,-1,0,0
,-1,0,0
,-1,0,0
,-1,0,0
//##### LEFT #####
,0,-1,0
,0,-1,0
,0,-1,0
,0,-1,0
,0,-1,0
,0,-1,0
//#####  BACK #####
 ,0,0,-1
,0,0,-1
,0,0,-1
,0,0,-1
,0,0,-1
,0,0,-1 

    ];
this.uvCoords = [
  //##### FRONT #####  Number: 1

        0, 1
      ,1/2,2/3
      , 0,2/3

      , 0, 1
      ,1/2,2/3
      ,1/2, 1

  //##### RIGHT ##### Number: 2

      , 0,2/3
      ,1/2,1/3
      , 0,1/3

      , 0,2/3
      ,1/2,1/3
      ,1/2,2/3

  //##### TOP ##### Number: 3

      , 0,1/3
      ,1/2, 0
      , 0, 0

      , 0,1/3
      ,1/2, 0
      ,1/2,1/3

  //##### LEFT ##### Number: 5

      ,1/2,2/3
      , 1,1/3
      ,1/2,1/3

      ,1/2,2/3
      , 1,1/3
      , 1,2/3

  //###### BOTTOM ##### Number: 4

      ,1/2, 1
      , 1,2/3
      ,1/2,2/3
      
      ,1/2, 1
      , 1,2/3
      , 1, 1

  //####### BACK ####### Number: 6

      ,1,1/3
      ,1/2, 0
      ,1/2,1/3

      , 1,1/3
      ,1/2, 0
      , 1, 0

    ];

}

TriangleMesh.prototype.createSphere = function(numStacks, numSectors) {
  //initialize the neccesary arrays to build the sphere
  this.indices = [];
  this.normals = [];
  this.positions = [];
  this.uvCoords = [];

  //calculate the step size, effectively the size of a sector or stack
  let secH = (2 * Math.PI / numSectors);
  let stackH = Math.PI / numStacks;

  //for all of the sectors and all of the stacks
  for(let i = 0.00; i <= numStacks; ++i){
    for(let j = 0.00; j <= numSectors; ++j){

      //calculate the xyz coordinates of all triangles within the sphere
      let xyz = spherexyz(i,j,stackH,secH);
      //add them to the correct array
      this.positions.push(xyz[0]);
      this.positions.push(xyz[1]);
      this.positions.push(xyz[2]);

      //Normals are just the vertex vectors of the triangles
      this.normals.push(xyz[0]);
      this.normals.push(xyz[1]);
      this.normals.push(xyz[2]);
    }
  }

  //calculate the indicies of for the sphere
  for(let i = 0.00; i <= numStacks; ++i){
    let k1k2 = sphereindices(i, numSectors);
    for(let j = 0.00; j <= numSectors; ++j, ++k1k2[0], ++k1k2[1]){
      if(i != 0){
        this.indices.push(k1k2[0]);
        this.indices.push(k1k2[1]);
        this.indices.push(k1k2[0] + 1);
      }
      if(i != (numStacks - 1)){
        this.indices.push(k1k2[0] + 1);
        this.indices.push(k1k2[1]);
        this.indices.push(k1k2[1] + 1);
      }
    }
  }
  //calculate the UV coordinates for the sphere
  for(var i = 0; i <= numStacks ; i++){
    for(var j = 0; j <= numSectors; j++){
      let u = j/numSectors;
      let v = i/numStacks;
      this.uvCoords.push(u);
      this.uvCoords.push(v);
    }
  }
}

//function for computing the transformation matrices 
Scene.prototype.computeTransformation = function(transformSequence) {
  // TODO: go through transform sequence and compose into overallTransform
  let matrices = [];
  //for use in for loop
  let max_index = transformSequence.length -1;

  //go through the transformations 1by1
  for(var i = 0; i <= max_index; i++){
    //Take the text iunput and store it in a variable
    let transform_type  = transformSequence[i][0];

    //the type of transform is a rotation
    if(transform_type.length == 2){
      if(transform_type[1] == 'x'){

        //convert to radians, will be done for all subsequent rotations
        let radians = transformSequence[i][1] * -(Math.PI/180);
        let mat = Mat4.create();
        //as described in the lectures. As for all of these transformations that can be easily found online if needed
        //4x4
        Mat4.set(mat,1,0,0,0,0,Math.cos(radians),-Math.sin(radians),0,0,Math.sin(radians),Math.cos(radians),0,0,0,0,1);
        matrices.push(mat);

      }
      else if(transform_type[1] == 'y'){

        let radians =transformSequence[i][1] * -(Math.PI/180);
        let mat = Mat4.create();
        Mat4.set(mat,Math.cos(radians),0,Math.sin(radians),0,0,1,0,0,-Math.sin(radians),0,Math.cos(radians),0,0,0,0,1);
        matrices.push(mat);

      }else{

        let radians =transformSequence[i][1] * -(Math.PI/180);
        let mat = Mat4.create();
        Mat4.set(mat,Math.cos(radians),-Math.sin(radians),0,0,Math.sin(radians),Math.cos(radians),0,0,0,0,1,0,0,0,0,1);
        matrices.push(mat);

      }
    }//the ype of transformation is not a rotation
    else{
      //translation
      if(transform_type[0] == 'T'){

        let x = transformSequence[i][1];
        let y = transformSequence[i][2];
        let z = transformSequence[i][3];
        let mat = Mat4.create();
        Mat4.set(mat,1,0,0,0,0,1,0,0,0,0,1,0,x,y,z,1);
        matrices.push(mat);

      }else{//scaling

        let x = transformSequence[i][1];
        let y = transformSequence[i][2];
        let z = transformSequence[i][3];
        let mat = Mat4.create();
        Mat4.set(mat,x,0,0,0,0,y,0,0,0,0,z,0,0,0,0,1);
        matrices.push(mat);

      }
    }
  }
  //trasnformations need to be applied right the left
  matrices.reverse();

  let overallTransform = matrices[0];
  for(var j = 1; j < matrices.length; j++){
    overallTransform = Mat4.multiply(overallTransform,overallTransform,matrices[j]);
  }
  return overallTransform;
}

Renderer.prototype.VERTEX_SHADER = `
precision mediump float;
attribute vec3 position, normal;
attribute vec2 uvCoord;
uniform vec3 lightPosition;
uniform mat4 projectionMatrix, viewMatrix, modelMatrix;
uniform mat3 normalMatrix;
varying vec2 vTexCoord;

// TODO: implement vertex shader logic below

varying vec3 temp;
varying vec3 fNormal;
varying vec3 fPosition;
varying vec3 lightDirection;
varying vec3 lightDirectionNN;
varying mat4 modelViewMatrix;

varying float dist;

void main() {
  //as done in class
  vTexCoord = uvCoord;
  temp = vec3(position.x, normal.x, uvCoord.x);


  lightDirection = normalize(lightPosition - position);
  lightDirectionNN = lightPosition - position;

  modelViewMatrix = viewMatrix * modelMatrix;
  fNormal = normalize(normalMatrix * normal);

  vec4 pos = modelViewMatrix * vec4(position,1.0);
  fPosition = pos.xyz;
  gl_Position = projectionMatrix * pos;

  dist = -(modelViewMatrix * pos).z;
}
`;

Renderer.prototype.FRAGMENT_SHADER = `
precision mediump float;
uniform vec3 ka, kd, ks, lightIntensity;
uniform float shininess;
uniform sampler2D uTexture;
uniform bool hasTexture;
varying vec2 vTexCoord;

// TODO: implement fragment shader logic below
varying vec3 temp;
varying vec3 fNormal;
varying vec3 fPosition;
varying vec3 lightDirection;
varying vec3 lightDirectionNN;

varying float dist;


void main() {
  //as done in class

  //(ambient)
  vec3 ca = ka * lightIntensity;

  //lambertian (diffuse)
  vec3 cd = (kd / dist) * max(0.0,dot(fNormal,lightDirection)) * lightIntensity;

  //Blinn-Phong (specular)
  vec3 v = -normalize(fPosition);
  vec3 r = reflect(-lightDirection,fNormal);
  vec3 h = normalize(v + lightDirection);
  vec3 cs = (ks / dist) * pow(max(0.0,dot(h,fNormal)), shininess) * lightIntensity;

  
  if(hasTexture){
    gl_FragColor = vec4(ca+cd+cs,1.0) * texture2D(uTexture, vTexCoord);
  }else{
    gl_FragColor = vec4(ca+cd+cs,1.0);
  }
}
`;

////////////////////////////////////////////////////////////////////////////////
// EXTRA CREDIT: change DEF_INPUT to create something interesting!
////////////////////////////////////////////////////////////////////////////////
const DEF_INPUT = [
  "c,myCamera,perspective,5,5,5,0,0,0,0,1,0;",
  "l,myLight,point,0,5,0,2,2,2;",
 " p,unitCube,cube;",
  "p,unitSphere,sphere,20,20;",
  "p,unitSphere2,sphere,5,5;",
  "p,unitSphere3,sphere,3,3;",
  "p,unitSphere4,sphere,7,7;",
  
  "m,globeMat,0.3,0.3,0.3,0.7,0.7,0.7,1,1,1,5,globe.jpg;",
  "o,gl,unitSphere,globeMat;",
  "m,globeMat2,0.3,0.3,0.3,0.7,0.7,0.7,1,1,1,5,globe.jpg;",
  "o,gl2,unitSphere2,globeMat2;",
  "m,globeMat3,0.3,0.3,0.3,0.7,0.7,0.7,1,1,1,5,globe.jpg;",
  "o,gl3,unitSphere3,globeMat3;",
  "m,globeMat4,0.3,0.3,0.3,0.7,0.7,0.7,1,1,1,5,globe.jpg;",
 " o,gl4,unitSphere4,globeMat4;",
  
  "m,redDiceMat,1,0,0,0.7,0,0,1,1,1,15,dice.jpg;",
  "m,grnDiceMat,0,0.3,0,0,0.7,0,1,1,1,15,dice.jpg;",
  "m,bluDiceMat,0,0,0.3,0,0,0.7,1,1,1,15,dice.jpg;",
  
  "m,redDiceMat2,0.3,0,0,0.7,0,0,1,1,1,15,dice.jpg;",
  "m,grnDiceMat2,0,0.2,0,0,0.7,0,1,1,1,15,dice.jpg;",
  "m,bluDiceMat2,0,0.3,0.3,0,0,0.7,1,1,1,15,dice.jpg;",
  
  "m,redDiceMat3,0.09,0,0,0.7,0,0,1,1,1,15,dice.jpg;",
  "m,grnDiceMat3,0,0.09,0,0,0.7,0,1,1,1,15,dice.jpg;",
  "m,bluDiceMat3,0.1,0.5,0.3,0,0,0.7,1,1,1,15,dice.jpg;",
  
  "o,rd,unitCube,redDiceMat;",
  "o,gd,unitCube,grnDiceMat;",
  "o,bd,unitCube,bluDiceMat;",
  
 " o,rd2,unitCube,redDiceMat2;",
  "o,gd2,unitCube,grnDiceMat2;",
  "o,bd2,unitCube,bluDiceMat2;",
  
  "o,rd3,unitCube,redDiceMat3;",
  "o,gd3,unitCube,grnDiceMat3;",
  "o,bd3,unitCube,bluDiceMat3;",
  
  "X,rd,Rz,75;X,rd,Rx,90;X,rd,S,0.5,0.5,0.5;X,rd,T,-1,0,2;",
  "X,gd,Ry,45;X,gd,S,0.5,0.5,0.5;X,gd,T,2,0,2;",
  "X,bd,S,0.5,0.5,0.5;X,bd,Rx,90;X,bd,T,2,0,-1;",
  
  "X,rd2,Rz,75;X,rd2,Rx,90;X,rd2,S,0.4,0.4,0.4;X,rd2,T,-0.85,1,2.1;",
  "X,gd2,Ry,45;X,gd2,S,0.4,0.4,0.4;X,gd2,T,2.08,1,2.08;",
  "X,bd2,S,0.4,0.4,0.4;X,bd2,Rx,90;X,bd2,T,2.08,1,-.85;",
  
  
  "X,rd3,Rz,75;X,rd3,Rx,90;X,rd3,S,0.3,0.3,0.3;X,rd3,T,-0.35,2,2.35;",
  "X,gd3,Ry,45;X,gd3,S,0.3,0.3,0.3;X,gd3,T,2.32,2,2.32;",
  "X,bd3,S,0.3,0.3,0.3;X,bd3,Rx,90;X,bd3,T,2.35,2,-.35;",
  
  "X,gl,S,0.8,0.8,0.8;X,gl,Rx,90;X,gl,Ry,-150;X,gl,T,1.7,2.5,-1;",
  "X,gl2,S,0.8,0.8,0.8;X,gl2,Rx,90;X,gl2,Ry,-150;X,gl2,T,-0.3,2.5,1;",
  "X,gl3,S,0.8,0.8,0.8;X,gl3,Rx,90;X,gl3,Ry,-150;X,gl3,T,-1.3,2.5,2;",
  "X,gl4,S,0.8,0.8,0.8;X,gl4,Rx,90;X,gl4,Ry,-150;X,gl4,T,0.7,2.5,0;"

].join("\n");

// DO NOT CHANGE ANYTHING BELOW HERE
export { Parser, Scene, Renderer, DEF_INPUT };
