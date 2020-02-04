<%@ page import="org.Database" %>
<%@ page  import= "java.sql.Connection"%>
<%@ page import= "java.sql.*"%>
<%@ page import ="java.util.Arrays"%>
<%@ page import ="java.util.Scanner"%>
<%@ page import ="java.util.logging.Level"%>
<%@ page import ="java.util.logging.Logger"%>
         
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

  <title>Craneware</title>

</head>

<body>

<!-- search options left-->
  <div class="container-fluid">
    <h1 style="text-align:center">Craneware</h1>

    <hr>
    
    <!--<form action="search" method="post">-->
        
            
            <div class="row">

              <!-- Search location-->
              <div class = "col-sm-5">
                <div class="md-form mt-0">
                  <!-- Search bar -->
                  <p style ="height:15px;"> </p>
                  <div class="input-group mt-3 mb-3">
                    <div class="input-group-prepend">

                      <!-- Script to change the location type -->
                      <script>
                      function switchLocationSearchType(selection) {
                        var selectedType = selection;
                        document.getElementById("selectedType").innerHTML = selectedType;
                      }
                      </script>


                      <button type="button" class="btn btn-outline-secondary dropdown-toggle" data-toggle="dropdown">
                        <a id="selectedType"> </a>
                      </button>
                      <div class="dropdown-menu">
                        <a class="dropdown-item" onclick="switchLocationSearchType('Zip')">Zip Code</a>
                        <a class="dropdown-item" onclick="switchLocationSearchType('Address')">Address</a>
                        <a class="dropdown-item" onclick="switchLocationSearchType('State')">State</a>

                      </div>
                    </div>
                    <input type="text" class="form-control" placeholder="Search by location...">
                  </div>
                  <p style ="height:15px;"> </p>

                  <!-- Search procedure -->
                  <div class="md-form mt-0">
                    <input class="form-control" type="text" placeholder="Search by procedure..." aria-label="Search" id="procedureInput">
                  </div>
                  <p style ="height:15px;"> </p>

                  <!-- Search code -->
                  <div class="md-form mt-0">
                    <input class="form-control" type="text" placeholder="Search by procedure code..." aria-label="Search" id="codeInput">
                  </div>
                </div>
                <p style ="height:15px;"> </p>

                <p style = "text-align:center;"><button onclick="displaySearchResults()" class="btn btn-outline-primary" style="width:220px">Search</button></p>
                <p></p>
              </div>



              <!-- search options right -->
              <div class="col-sm-7">
                <h4>Filters:</h4>

                <!-- dropdown select price range -->

                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">�</span>
                    </div>
                    <input type="text" class="form-control" placeholder="Maximum Price..." aria-label="Amount (to the nearest pound)">
                    <div class="input-group-append">
                      <span class="input-group-text">.00</span>
                    </div>
                  </div>

                  <p></p>


                  <!-- dropdown select distance range -->
                <div class="input-group mb-3">
                  <input type="text" class="form-control" placeholder="Maximum Distance..." aria-label="Maximum Distance" aria-describedby="basic-addon2">
                  <div class="input-group-append">
                    <span class="input-group-text" id="basic-addon2">Km</span>
                  </div>
                </div>

                  <p></p>

                  <h4>Sort by:</h4>

                  <!-- dropdown select highest to lowest price -->
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <label class="input-group-text" for="inputGroupSelect01">Price</label>
                    </div>
                    <select class="custom-select" id="inputGroupSelect01">
                      <option selected>Choose...</option>
                      <option value="1">None</option>
                      <option value="2">Low - High</option>
                      <option value="3">High - Low</option>
                    </select>
                  </div>

                  <p></p>
                  <!-- dropdown select closest to furthest distance -->
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <label class="input-group-text" for="inputGroupSelect01">Distance</label>
                    </div>
                    <select class="custom-select" id="inputGroupSelect01">
                      <option selected>Choose...</option>
                      <option value="1">None</option>
                      <option value="2">Low - High</option>
                      <option value="3">High - Low</option>
                    </select>
                  </div>


                </div>
              </div>
        
    <!--</form>-->

      <hr>

      <div class="container-fluid">
        <div class="row">
          <div class = "col-sm-6">
            <!-- search results -->
            <h1 id="searchResults">Search results</h1>
            
            
            
            <!--<script>

                
                
                function displaySearchResults() {
                    
                    var searchResultNameArray = ["ResultName1", "ResultName2", "ResultName3"];

                    var searchResultAddressArray = 
                    
                    var searchResultProcedureArray = 
                    
                    var searchResultPaymentsArray = ["ResultPayments1", "ResultsPayments2", "ResultPayments3"];


                    for (var i in searchResultNameArray) {
                        

                        var newDiv = document.createElement("div")
                        
                        newDiv.className = "container p-3 my-3 border";
                        
                        var name = document.createElement("h2");
                        var newName = document.createTextNode(searchResultNameArray[i]);
                        name.appendChild(newName);
                        
                        newDiv.appendChild(name);
                        
                        var address = document.createElement("p");
                        var newAddress = document.createTextNode(searchResultAddressArray[i]);
                        address.appendChild(newAddress);
                        
                        newDiv.appendChild(address);
                        
                        var procedure = document.createElement("p");
                        var newProcedure = document.createTextNode(searchResultProcedureArray[i]);
                        procedure.appendChild(newProcedure);
                        
                        newDiv.appendChild(procedure);
                        
                        var name = document.createElement("p");
                        var newName = document.createTextNode(searchResultNameArray[i]);
                        name.appendChild(newName);
                        
                        newDiv.appendChild(name);
                        
                        var currentDiv = document.getElementById("searchResults");
                        
                        $("#display").append(newDiv);
                        
                        /**document.body.insertBefore(newDiv, currentDiv);*/

                       /**
                       var name = document.createElement("h2");
                       name.innerHTML = searchResultNameArray[i];
                       $("#display").append(name);
                       */
                       
                    }
                };
                
            </script>-->
            
            <a id="display"></a>
            
            <% Database db = new Database();
            ResultSet result = db.runSearchConditionP("989");
            String[] conditions = db.parseCondition(result);
            String[] addresses = db.parseAddress(result);
            String[] provNames = db.parseProviderName(result);
            Double[] avgMeds = db.parseAvgMed(result);
            %>
            
            <div class ="container p-3 my-3 border">
                <div class ="row">
                    <h1><%out.print(provNames[0]);%></h1>
              <p><%out.print(addresses[0]);%></p>
              <p><%out.print(conditions[0]);%></p>
              <p>Average medical care payments: $<%out.print(avgMeds[0]);%></p>
                </div>
                <div class ="row">
              <h1><%out.print(provNames[1]);%></h1>
              <p><%out.print(addresses[1]);%></p>
              <p><%out.print(conditions[1]);%></p>
              <p>Average medical care payments: $<%out.print(avgMeds[1]);%></p>
                </div>
              <div class ="row">
              <h1><%out.print(provNames[2]);%></h1>
              <p><%out.print(addresses[2]);%></p>
              <p><%out.print(conditions[2]);%></p>
              <p>Average medical care payments: $<%out.print(avgMeds[2]);%></p>
              </div>
            </div>
            

          </div>

          <div class = "col-sm-6">

            <div class="card sticky-top">
              <!-- map -->
              <h1>Map</h1>
            </div>


          </div>

        </div>


      </div>


    </body>

</html>

