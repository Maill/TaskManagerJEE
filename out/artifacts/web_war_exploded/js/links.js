/****************************************************************************************************/

if(document.getElementById('navigationBarLinksHome')) document.getElementById('navigationBarLinksHome').addEventListener('click', function()
{
    location = '/list';
});

/****************************************************************************************************/

if(document.getElementById('navigationBarLinksCreation')) document.getElementById('navigationBarLinksCreation').addEventListener('click', function()
{
    location = '/creation';
});

/****************************************************************************************************/

function openModificationPage(taskID)
{
    location = '/modification?task=' + taskID;
}

/****************************************************************************************************/