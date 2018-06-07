/****************************************************************************************************/

var currentDate = new Date(Date.now());

var minimumDelayBeforeEndOfTask = 10;

if(document.getElementById('yearSelect'))
{
    for(var i = 0; i < 10; i++)
    {
        document.getElementById('yearSelect').innerHTML += '<option value="' + (currentDate.getFullYear() + i) + '">' + (currentDate.getFullYear() + i) + '</option>';
    }
}

if(document.getElementById('yearSelect')) document.getElementById('yearSelect').addEventListener('change', function(){ updateMonthSelect(document.getElementById('yearSelect').options[document.getElementById('yearSelect').selectedIndex].value); });
if(document.getElementById('monthSelect')) document.getElementById('monthSelect').addEventListener('change', function(){ updateDaySelect(document.getElementById('monthSelect').options[document.getElementById('monthSelect').selectedIndex].value); });
if(document.getElementById('daySelect')) document.getElementById('daySelect').addEventListener('change', function(){ updateHourSelect(document.getElementById('daySelect').options[document.getElementById('daySelect').selectedIndex].value); });
if(document.getElementById('hourSelect')) document.getElementById('hourSelect').addEventListener('change', function(){ updateMinuteSelect(document.getElementById('hourSelect').options[document.getElementById('hourSelect').selectedIndex].value); });

/****************************************************************************************************/

function updateMonthSelect(yearSelected)
{
    updateDaySelect('');

    yearSelected.length == 0 ? document.getElementById('monthSelect').innerHTML = '<option></option>' :

    removeOptionsFromSelect('monthSelect', function(error)
    {
        for(var i = 0; i < 12; i++)
        {
            if(currentDate.getFullYear() == yearSelected)
            {
                if(i >= currentDate.getMonth())
                {
                    document.getElementById('monthSelect').innerHTML += '<option value="' + i + '">' + (i + 1) + '</option>';
                }
            }

            else
            {
                document.getElementById('monthSelect').innerHTML += '<option value="' + i + '">' + (i + 1) + '</option>';
            }
        }
    });
}

/****************************************************************************************************/

function updateDaySelect(monthSelected)
{
    updateHourSelect('');

    monthSelected.length == 0 ? document.getElementById('daySelect').innerHTML = '<option></option>' :

    removeOptionsFromSelect('daySelect', function(error)
    {
        var amountOfDaysInSelectedMonth = new Date(parseInt(document.getElementById('yearSelect').options[document.getElementById('yearSelect').selectedIndex].value), parseInt(monthSelected) + 1, 0).getDate();

        var startingDay = (monthSelected == currentDate.getMonth()) && (document.getElementById('yearSelect').options[document.getElementById('yearSelect').selectedIndex].value == currentDate.getFullYear()) ? currentDate.getDate() : 1;

        for(var i = startingDay; i <= amountOfDaysInSelectedMonth; i++)
        {
            document.getElementById('daySelect').innerHTML += '<option value="' + i + '">' + i + '</option>';
        }
    });
}

/****************************************************************************************************/

function updateHourSelect(daySelected)
{
    updateMinuteSelect('');

    daySelected.length == 0 ? document.getElementById('hourSelect').innerHTML = '<option></option>' :

    removeOptionsFromSelect('hourSelect', function(error)
    {
        for(var i = 0; i < 24; i++)
        {
            if((daySelected == currentDate.getDate()) && (document.getElementById('monthSelect').options[document.getElementById('monthSelect').selectedIndex].value == currentDate.getMonth()) && (document.getElementById('yearSelect').options[document.getElementById('yearSelect').selectedIndex].value == currentDate.getFullYear()))
            {
                if(i > currentDate.getHours())
                {
                    document.getElementById('hourSelect').innerHTML += '<option value="' + i + '">' + i + '</option>';
                }

                else if((i == currentDate.getHours()) && ((currentDate.getMinutes() + minimumDelayBeforeEndOfTask) < 60))
                {
                    document.getElementById('hourSelect').innerHTML += '<option value="' + i + '">' + i + '</option>';
                }
            }

            else
            {
                document.getElementById('hourSelect').innerHTML += '<option value="' + i + '">' + i + '</option>';
            }
        }
    });
}

/****************************************************************************************************/

function updateMinuteSelect(hourSelected)
{
    hourSelected.length == 0 ? document.getElementById('minuteSelect').innerHTML = '<option></option>' :

    removeOptionsFromSelect('minuteSelect', function(error)
    {
        for(var i = 0; i < 60; i++)
        {
            if((hourSelected == currentDate.getHours()) && (document.getElementById('daySelect').options[document.getElementById('daySelect').selectedIndex].value == currentDate.getDate()) && (document.getElementById('monthSelect').options[document.getElementById('monthSelect').selectedIndex].value == currentDate.getMonth()) && (document.getElementById('yearSelect').options[document.getElementById('yearSelect').selectedIndex].value == currentDate.getFullYear()))
            {
                if(i >= (currentDate.getMinutes() + minimumDelayBeforeEndOfTask))
                {
                    document.getElementById('minuteSelect').innerHTML += '<option value="' + i + '">' + i + '</option>';
                }
            }

            else
            {
                document.getElementById('minuteSelect').innerHTML += '<option value="' + i + '">' + i + '</option>';
            }
        }
    });
}

/****************************************************************************************************/

function removeOptionsFromSelect(selectID, callback)
{
    var x = 1;

    var removeOptionsLoop = function()
    {
        document.getElementById(selectID).children[x].remove();

        document.getElementById(selectID).children[x] != undefined
        ? removeOptionsLoop()
        : callback(null);
    }

    document.getElementById(selectID).children[x] != undefined
    ? removeOptionsLoop()
    : callback(null);
}

/****************************************************************************************************/