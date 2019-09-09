'use strict'

let data;
function httpGetAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}
function httpPostAsync(theUrl,jsonToSend, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 201)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("POST", theUrl, true); // true for asynchronous
    xmlHttp.setRequestHeader("Content-type", "application/json");

    xmlHttp.send(jsonToSend);
}

function httpDeleteAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("DELETE", theUrl, true); // true for asynchronous
//    xmlHttp.setRequestHeader("Content-type", "application/json");

    xmlHttp.send(null);
}

function httpPatchAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("PATCH", theUrl, true); // true for asynchronous

    xmlHttp.send(null);
}



// Basic Page Switching Code
function loadHome(){
    document.getElementById("entries-container").innerHTML=`
<div id="search-section">
    <!-- contains search box and add button -->
    Search And Add
    <input class="pad" type="text" id="search-box">
    
    <button class="pad" id="add-button" disabled="disabled" onclick="addTask()">ADD</button>
    <div id="search-results"></div>
</div>
<div id="entries">
    <!-- Entries and Actions Table Here -->
    <table>
        <thead>
            <tr>
                <th>Task</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody id="entry-table">
            <tr>
                <td>Pseudo Row</td>
                <td><button>Edit</button> <button>Remove</button></td>
            </tr>
        </tbody>
    </table>
</div>`;
// document.getElementById("search-box").addEventListener("keydown",eve=>{searchBoxQuery()});
document.getElementById("search-box").addEventListener("keyup",eve=>{
    searchBoxQuery();
    if (eve.keyCode === 13) {
        eve.preventDefault();
        document.getElementById("add-button").click();
      }
});
populateTable();
}

function loadUses(){
    document.getElementById("entries-container").innerHTML=`To-Do List Can Become a major part of your life.
                                                        All You have to do is to complete all the tasks present in this list.`
}

function loadAbout(){
    document.getElementById("entries-container").innerHTML=`This application is create by Aniket Singla with the use of HTML, CSS and Vanilla
    JavaScript. This is just a User Interface without any backend and hence all your saved data may disappear on shutdown of this application or Browser`
}

// Main Code 

function populateTable(){
    let htmlCode = '';
    httpGetAsync('/tasks',(json)=>{
        data = JSON.parse(json);
        data.forEach(element => {
                htmlCode += `<tr>
                                <td>${element.name}</td>
                                <td><button onclick="editNameWizard(${data.indexOf(element)})">Edit</button> <button onclick="removeName(${data.indexOf(element)})">Remove</button></td>
                            </tr>`
            });
        document.getElementById("entry-table").innerHTML = htmlCode;
    })


}
function editNameWizard(index){
    document.getElementById("entries-container").innerHTML =   `<h5>Edit Task Wizard:</h5>
                                                                <input type="text" placeholder="${data[index].name}" id="edit-name">
                                                                <button onclick="editName(${index})">Save</button>`
    
}

function editName(index){
    let newValue = document.getElementById("edit-name").value;
    //    localStorage.data = JSON.stringify(data);
    httpPatchAsync(`/tasks/${data[index].id}?newTask=${newValue}`,()=>{
        data[index].name = newValue;
            loadHome();
    });

}

function removeName(index){
    //    localStorage.data = JSON.stringify(data);
    httpDeleteAsync(`/tasks/${data[index].id}`,()=>{
        data.splice(index,1);
        loadHome();
    });

}

/*
    *Search And Add Functionality
*/

function searchBoxQuery()
{
    let toFind = document.getElementById('search-box').value.trim();
    let list=[];
    data.forEach(element=>{
        if(element.name.toLowerCase().startsWith(toFind.toLowerCase())){
            list.push(element);
        }     
        
    })
    let button = document.getElementById("add-button");
    if((list.length>0 && list[0].name.toLowerCase()==toFind.toLowerCase())||toFind.trim()=="")
    {
        button.disabled=true;
        button.style.backgroundColor = "red";
    }
            
    else
    {
        button.disabled = false;
        button.style.backgroundColor = "green";
        
    }
    let htmlCode="";
    list.forEach(element => {
        htmlCode += `<tr>
                        <td>${element.name}</td>
                        <td><button onclick="editNameWizard(${data.indexOf(element)})">Edit</button> <button onclick="removeName(${data.indexOf(element)})">Remove</button></td>
                    </tr>`
    });
    document.getElementById("entry-table").innerHTML = htmlCode;
}

function addTask(){
    let name = document.getElementById('search-box').value;
    let nextId;
    if(data.length>0)
        nextId = data[data.length-1].id + 1;
    else
        nextId = 1;
    console.log(nextId)
//    localStorage.data = JSON.stringify(data);
    httpPostAsync('/tasks',`{"id": ${nextId},"name": "${name}"}`,(text)=>{
        console.log(text)
        data[data.length] = {id : nextId,name: name};
        loadHome();
    })


}

    populateTable();
window.addEventListener("load", function() {
    document.getElementById("search-box").addEventListener("keyup",eve=>{
        searchBoxQuery();
        if (eve.keyCode === 13) {
            eve.preventDefault();
            document.getElementById("add-button").click();
          }
    });
});
