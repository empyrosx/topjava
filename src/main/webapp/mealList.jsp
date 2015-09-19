<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список приёмов пищи</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
    <style type="text/css">
        table.meals {
            border-collapse: collapse;
        }

        table.meals td,
        table.meals th {
            border: 1px solid black;
            padding: 5px;
        }

        tr.highlighted td {
            background-color: indianred;
        }
    </style>
</head>
<body>

<form action="<c:url value="/meals"/>" method="post">

    <p/>
    <table class="meals">
        <tr>
            <th></th>
            <th>Дата</th>
            <th>Время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>

        <c:forEach var="meal" items="${meals}">
            <c:set var="rowClass" value=""/>
            <c:if test="${meal.exceed}">
                <c:set var="rowClass" value="highlighted"/>
            </c:if>
            <tr class="<c:out value="${rowClass}"/>">
                <td><input type="radio" name="mealId" value="${meal.id}"></td>
                <f:parseDate value="${meal.date}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
                <td><f:formatDate value="${parsedDate}" pattern="yyyy-MM-dd"/></td>
                <f:parseDate value="${meal.time}" pattern="HH:mm" var="parsedTime" type="time"/>
                <td><f:formatDate value="${parsedTime}" pattern="HH:mm"/></td>
                <td><c:out value="${meal.description}"></c:out></td>
                <td><c:out value="${meal.calories}"></c:out></td>
            </tr>
        </c:forEach>
    </table>

    <table>
        <tr>
            <td>
                <button type="submit" name="action" value="add">Добавить</button>
            </td>
            <td>
                <button type="submit" name="action" value="edit">Редактировать</button>
            </td>
            <td>
                <button type="submit" name="action" value="remove">Удалить</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>