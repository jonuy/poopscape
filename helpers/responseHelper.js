module.exports = {
  /**
   * Standardizes and sends the error response.
   *
   * @param res Express response object
   * @param code Error status code
   * @param msg String message
   */
  sendError: function(res, code, msg) {
    res.status(code).send({error: msg});
  }
};