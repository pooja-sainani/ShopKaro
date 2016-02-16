using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class UserService
    {
        /// <summary>
        /// user roles
        /// </summary>
        private enum UserRoles
        {
            Admin,
            Seller,
            Buyer
        }

        /// <summary>
        /// Add new Seller/ Buyer
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        public Guid AddNewUser(USER user)
        {
            try
            {
                if (user == null)
                    throw new ArgumentNullException("User", "User can not be null");

                if(user.ROLEID == (int)UserRoles.Admin)
                    throw new ArgumentException("User", "Admins can not be added using this service ");

                // check if all required fields are present
                if (user.USERNAME == null || user.PASSWORD == null || user.NAME == null || user.GENDER == null || user.CONTACTNUMBER == null)
                    throw new ArgumentException("User", "Some mandatory parameters required to add a new user are missing");

                // by default, all users are buyers
                if (!user.ROLEID.HasValue) user.ROLEID = (int)UserRoles.Buyer;

                // check if all additional required fields for seller are present
                if (user.ROLEID == (int)UserRoles.Seller && 
                    (user.EMAILID == null || user.PLACE == null || user.CITY == null || user.STATE == null || user.PINCODE == null))
                    throw new ArgumentException("User", "Some additional mandatory parameters required to add a new seller are missing");

                if (!user.ID.HasValue || user.ID.Value == Guid.Empty)
                    user.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.USERS.Add(user);
                        db.SaveChanges();
                    }
                    catch(System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }     
                    catch(Exception e)
                    {
                        throw;
                    }             
                }
                    
                return user.ID.Value;
            }
            catch(Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Edit buyer/ seller profile details
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        public bool EditUserDetails(USER user)
        {
            try
            {
                if (user == null)
                    throw new ArgumentNullException("User", "User can not be null");

                if (user.ID == null)
                    throw new ArgumentNullException("User", "User ID can not be null");                             

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        USER existingUser = (from u in db.USERS
                                             where u.ID == user.ID
                                             select u).Single();

                        if (user.ROLEID != null && existingUser.ROLEID != user.ROLEID)
                            throw new ArgumentException("ROLEID can not be changed using this service");
                        if(user.USERNAME != null && existingUser.USERNAME != user.USERNAME)
                            throw new ArgumentException("ROLEID can never be modified");

                        if (user.NAME != null) existingUser.NAME = user.NAME;
                        if (user.PASSWORD != null) existingUser.PASSWORD = user.PASSWORD;
                        if (user.CONTACTNUMBER != null) existingUser.CONTACTNUMBER = user.CONTACTNUMBER;
                        if (user.EMAILID != null) existingUser.EMAILID = user.EMAILID;
                        if (user.PLACE != null) existingUser.PLACE = user.PLACE;
                        if (user.CITY != null) existingUser.CITY = user.CITY;
                        if (user.STATE != null) existingUser.STATE = user.STATE;
                        if (user.PINCODE != null) existingUser.PINCODE = user.PINCODE;

                        db.SaveChanges();
                    }

                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (Exception e)
                    {
                        throw;
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                throw;
            }
        }

    }
}