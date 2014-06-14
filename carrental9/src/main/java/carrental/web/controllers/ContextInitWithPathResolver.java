package carrental.web.controllers;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import carrental.web.PathAuthResolver;
import carrental.web.PathAuthResolverImpl;

@WebListener
public class ContextInitWithPathResolver implements ServletContextListener {

	private ServletContext ctx;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ctx = servletContextEvent.getServletContext();
		PathAuthResolver pathAuthResolver = new PathAuthResolverImpl(getJspPaths());
		ctx.setAttribute("pathAuthResolver", pathAuthResolver);
	}

	private Collection<String> getJspPaths() {
		return getJspPaths(ctx.getRealPath("WEB-INF/views"));
	}

	private Collection<String> getJspPaths(String root) {
		Collection<String> pathes = new HashSet<String>();
		findJspPaths(root, pathes);
		return pathes;
	}

	private void findJspPaths(String candidatePath, Collection<String> pathes) {
		if (candidatePath.endsWith(".jsp")) {
			pathes.add(candidatePath);
			return;
		}
		File file = new File(candidatePath);
		if (file.isDirectory())
			for (String subFileName : file.list())
				findJspPaths(candidatePath + "\\" + subFileName, pathes);
	}

}
