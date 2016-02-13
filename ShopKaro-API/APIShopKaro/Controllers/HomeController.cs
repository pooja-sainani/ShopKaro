using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Http;

using System.Web.Http.Description;

using APIShopKaro.Models;
using Newtonsoft.Json;

namespace APIShopKaro.Controllers
{

    using System.Configuration;
    using System.IO;
    using System.Security.Permissions;
    using System.Threading;

    //   using Microsoft.WindowsAzure.Storage.File;

    // using MS.IT.Common.Notification;

    using HttpPostAttribute = System.Web.Mvc.HttpPostAttribute;
    using System.Security.Claims;
    using System.Diagnostics;
    public class HomeController : ApiController
    {

        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("api/Login")]
       
        [ResponseType(typeof(USER))]
        
        public IHttpActionResult Login()
        {
           USER ui = null;
            try
            {
                APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities();
                ui=  
                   (from userinfo in db.USERS  select userinfo)
                       .ToList()[0];
            }
            catch (Exception e)
            {
                return this.NotFound();
            }
            return Ok(ui);
        }
    }
}
