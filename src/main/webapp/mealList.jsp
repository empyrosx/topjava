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

        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>

<form action="<c:url value="/meals"/>" method="post">

    <p/>
    <a href="meals?action=create">Добавить прием пищи</a>
    <table class="meals">
        <tr>
            <th>Дата и время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach var="meal" items="${mealList}">
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <f:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate" type="date"/>
                <td><f:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><c:out value="${meal.description}"></c:out></td>
                <td><c:out value="${meal.calories}"></c:out></td>
                <td><a href="meals?action=update&id=${meal.id}">Изменить</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>