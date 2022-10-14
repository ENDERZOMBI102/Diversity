import com.enderzombi102.diversity.core.module.DiversityModules;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.Objects;

public class CoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient( ModContainer mod ) {
		for ( var module : DiversityModules.modules() )
			Objects.requireNonNull( module.main() ).getInstance().onInitialize( module.container() );
	}
}
