<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Task Manager - Suppression</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/navigation.css" />
</head>
    <body>
        <jsp:include page="navigation.jsp" />
        <div class="bodyLocation">Suppression</div>
        <c:choose>
            <c:when test="${success != null}">
                <c:choose>
                    <c:when test="${success}">
                        <div class="taskRemoveBlock">
                            <div class="taskRemoveBlockSuccess">Tâche supprimée avec succès</div>
                            <a class="taskRemoveBlockReturn" href="/list">Retour</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="taskRemoveBlock">
                            <div class="taskRemoveBlockError">Erreur pendant la suppression de la tâche</div>
                            <a class="taskRemoveBlockReturn" href="/list">Retour</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="taskRemoveBlock">
                    <div class="taskRemoveBlockTitle">Supprimer une tâche</div>
                    <div class="taskRemoveBlockMessage">Êtes-vous sûr(e) de vouloir supprimer cette tâche ?</div>
                    <a class="taskRemoveBlockConfirm" href="/suppression?remove=${taskID}">Oui</a><!--
                    --><a class="taskRemoveBlockCancel" href="/list">Non</a>
                </div>
            </c:otherwise>
        </c:choose>
        <script src="../js/links.js"></script>
    </body>
</html>
