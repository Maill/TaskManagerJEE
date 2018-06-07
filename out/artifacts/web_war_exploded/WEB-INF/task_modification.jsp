<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Task Manager - Modification</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/form.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/navigation.css" />
    </head>
<body>
<jsp:include page="navigation.jsp" />
<div class="bodyLocation">Modification</div>
<c:if test="${success == true}">
    <div class="successMessage">Tâche modifiée avec succès</div>
</c:if>
<c:if test="${success == false}">
    <div class="errorMessage">Modification échouée</div>
</c:if>
<div class="formContainer">
    <div class="formContainerTitle">Modifier une tâche</div>
    <form action="/modification" method="post">
        <input type="hidden" name="taskID" value="${task.id}" />
        <label class="formContainerLabel">Nom :</label>
        <input name="taskName" class="formContainerInput" type="text" placeholder="Nom de la tâche" value="${task.title}" required />
        <label class="formContainerLabel">Description :</label>
        <textarea class="formContainerInput" rows="6" maxlength="1024" placeholder="Détail de la tâche" name="taskDescription" required>${task.description}</textarea>
        <div class="formContainerDateBlock">
            <div class="formContainerDateBlockTitle">Date :</div>
            <div class="formContainerDateBlockCalendar">
                <label class="formContainerDateBlockCalendarLabel">Année :</label>
                <select class="formContainerSelect" id="yearSelect" name="taskYear" required>
                    <option></option>
                </select>
                <label class="formContainerDateBlockCalendarLabel">Mois :</label>
                <select class="formContainerSelect" id="monthSelect" name="taskMonth" required>
                    <option></option>
                </select>
                <label class="formContainerDateBlockCalendarLabel">Jour :</label>
                <select class="formContainerSelect" id="daySelect" name="taskDay" required>
                    <option></option>
                </select>
            </div>
            <div class="formContainerDateBlockClock">
                <label class="formContainerDateBlockClockLabel">Heure :</label>
                <select class="formContainerSelect" id="hourSelect" name="taskHour" required>
                    <option></option>
                </select>
                <label class="formContainerDateBlockClockLabel">Minute :</label>
                <select class="formContainerSelect" id="minuteSelect" name="taskMinute" required>
                    <option></option>
                </select>
            </div>
        </div>
        <label class="formContainerUrgencyLabel">Urgent :</label>
        <input class="formContainerUrgencyCheckbox" type="checkbox" name="taskUrgency" <c:if test="${task.urgent}">checked</c:if> />
        <input class="formContainerSubmit" type="submit" value="Envoyer" />
    </form>
</div>
<script src="../js/links.js"></script>
<script src="../js/formSelect.js"></script>
</body>
</html>

