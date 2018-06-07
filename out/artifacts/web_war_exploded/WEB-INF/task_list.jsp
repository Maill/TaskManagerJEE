<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Task Manager - Accueil</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/navigation.css" />
    </head>
    <body>
		<jsp:include page="navigation.jsp" />
		<div class="bodyLocation">Accueil</div>
        <c:if test="${urgent}">
            <a class="bodyLink" href="/list">Afficher toutes les tâches</a>
        </c:if>
        <c:if test="${!urgent}">
            <a class="bodyLink" href="/list?urgent=true">Afficher uniquement les tâches urgentes</a>
        </c:if>
        <div class="taskListContainer">
            <c:forEach items="${requestScope.tasks}" var="task">
                <div class="taskListContainerBlock">
                    <div class="taskListContainerBlockTitle">
                        <div class="taskListContainerBlockTitleLeft">${task.title}</div>
                        <c:if test="${task.isUrgent()}">
                            <div class="taskListContainerBlockTitleRight">URGENT !</div>
                        </c:if>
                    </div>
                    <div class="taskListContainerBlockDescription">${task.description}</div>
                    <div class="taskListContainerBlockDescription"><c:choose><c:when test="${task.scheduledDate.getDate() < 10}">0${task.scheduledDate.getDate()}</c:when><c:otherwise>${task.scheduledDate.getDate()}</c:otherwise></c:choose>/<c:choose><c:when test="${(task.scheduledDate.getMonth() + 1) < 10}">0${task.scheduledDate.getMonth() + 1}</c:when><c:otherwise>${task.scheduledDate.getMonth() + 1}</c:otherwise></c:choose>/${task.scheduledDate.getYear() + 1900} - <c:choose><c:when test="${task.scheduledDate.getHours() < 10}">0${task.scheduledDate.getHours()}</c:when><c:otherwise>${task.scheduledDate.getHours()}</c:otherwise></c:choose>:<c:choose><c:when test="${task.scheduledDate.getMinutes() < 10}">0${task.scheduledDate.getMinutes()}</c:when><c:otherwise>${task.scheduledDate.getMinutes()}</c:otherwise></c:choose></div>
                    <c:choose>
                        <c:when test="${task.scheduledDate.getTime() > currentDate}">
                            <div class="taskListContainerBlockPending">EN COURS</div>
                            <a href="/modification?task=${task.id}" class="taskListContainerBlockModify">Modifier</a><!--
                            --><a href="/suppression?task=${task.id}" class="taskListContainerBlockRemove">Supprimer</a>
                        </c:when>
                        <c:otherwise>
                            <div class="taskListContainerBlockEnded">TERMINÉE</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
        <script src="../js/links.js"></script>
    </body>
</html>